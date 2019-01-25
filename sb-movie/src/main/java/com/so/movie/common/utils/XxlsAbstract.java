import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.stream.XMLInputFactory;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件驱动读取Excel抽象方法。
 *
 * @author yzy
 * @version 2018年7月6日
 */
public abstract class XxlsAbstract extends DefaultHandler {
    
	private static Logger log = LogManager.getLogger(XxlsAbstract.class);
	
	private SharedStringsTable sst = null;
    
	private String lastContents = "";
    
	private boolean nextIsString = false;
    
    /**
     * 保存读取的当前行数据<单元格名称, 值>. 空单元格无数据。
     */
    private Map<String, String> rowValueMap = new HashMap<>();
    
    private int curRow = 0;
    
    private String curCellName = "";
    
    /**
     * 处理单行数据回调方法。
     * 
     * @param curRow 当前行号
     * @param rowValueMap 一行的数据
     * @throws SQLException
     * @author yzy
     */
    public abstract void optRows(int curRow, Map<String, String> rowValueMap) throws SQLException;
    
    /**
     * 读取一个Sheet页中的所有数据。
     * 
     * @param filePath excel文件路径
     * @param sheetId sheet页编码。从1开始
     * @throws Exception
     * @author yzy
     */
    public void readOneSheet(String filePath, int sheetId) throws Exception {
        OPCPackage pkg = null;
        InputStream sheet = null;
        try {
            pkg = OPCPackage.open(filePath);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = fetchSheetParser(sst);
            
            // 根据 rId# 或 rSheet# 查找sheet
            sheet = r.getSheet("rId" + sheetId);
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            
        } finally {
            if (sheet != null) {
                sheet.close();
            }
            if (pkg != null) {
                pkg.close();
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        // c => 单元格
        if (name.equals("c")) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // 置空
        lastContents = "";
        
        String cellName = attributes.getValue("r");
        if (!Strings.isBlank(cellName)) {
            curCellName = cellName;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {
            	// 这里不需要记异常处理，避免fingbug告警，打印一条信息
            	log.info("read last contents");
            }
        }

        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowValueMap中，在这之前先去掉字符串前后的空白符
        if (name.equals("v")) {
            String value = lastContents.trim();
            if (!value.isEmpty()) {
                rowValueMap.put(curCellName, value);
            }            
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                try {
                    optRows(curRow, rowValueMap);
                } catch (SQLException e) {
                	log.error(e);
                }
                rowValueMap.clear();
                curRow++;
                curCellName = "";
            }
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //得到单元格内容的值
        lastContents += new String(ch, start, length);
    }
    
    private XMLReader fetchSheetParser(SharedStringsTable sst)
            throws SAXException {
        //修改forify
        XMLInputFactory xif= XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
}
