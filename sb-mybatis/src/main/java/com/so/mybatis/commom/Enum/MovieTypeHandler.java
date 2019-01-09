package com.so.mybatis.commom.Enum;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieTypeHandler implements TypeHandler<MovieType> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MovieType movieType, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public MovieType getResult(ResultSet resultSet, String s) throws SQLException {
        return MovieType.str2Enum(resultSet.getString(s));
    }

    @Override
    public MovieType getResult(ResultSet resultSet, int i) throws SQLException {
        return MovieType.str2Enum(resultSet.getString(i));
    }

    @Override
    public MovieType getResult(CallableStatement callableStatement, int i) throws SQLException {
        return MovieType.str2Enum(callableStatement.getString(i));
    }
}
