package kr.pe.usee.excel;

import java.io.IOException;
import java.sql.SQLException;

import kr.pe.usee.db.mysql.MysqlDb;

public class ProdInsert {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		MysqlDb.insertSql();

	}

}
