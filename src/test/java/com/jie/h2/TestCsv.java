package com.jie.h2;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;

import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

public class TestCsv {
	public static void main(String[] args) throws Exception {
		write();
		read();
	}

	static void write() throws Exception {
		SimpleResultSet rs = new SimpleResultSet();
		rs.addColumn("NAME", Types.VARCHAR, 255, 0);
		rs.addColumn("EMAIL", Types.VARCHAR, 255, 0);
		rs.addRow("Bob Meier", "bob.meier@abcde.abc");
		rs.addRow("John Jones", "john.jones@abcde.abc");
		new Csv().write("data" + File.separator + "test.csv", rs, null);
	}

	static void read() throws Exception {
		ResultSet rs = new Csv().read("data" + File.separator + "test.csv",
				null, null);
		ResultSetMetaData meta = rs.getMetaData();
		while (rs.next()) {
			for (int i = 0; i < meta.getColumnCount(); i++) {
				System.out.println(meta.getColumnLabel(i + 1) + ": "
						+ rs.getString(i + 1));
			}
			System.out.println();
		}
		rs.close();
	}
}