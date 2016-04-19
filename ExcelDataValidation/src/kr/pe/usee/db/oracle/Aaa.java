package kr.pe.usee.db.oracle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Aaa {
	
	

	String DB_URL = "jdbc:oracle:thin:@10.61.10.41:1521:SUPERNIS";
	String DB_USER = "supernis";
	String DB_PASSWORD = "supernis";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String query = null;
	
	public Aaa() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 데이터베이스의 연결을 설정한다.
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public Aaa(String string) throws ClassNotFoundException, SQLException  {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 데이터베이스의 연결을 설정한다.
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		query = string;
	}

	public static void main(String[] args) throws Exception {


		StringBuffer sb = new StringBuffer(); 
		sb.append("SELECT                                                                                                                             ");
		sb.append("      A.SALE_DY                                                                                             AS SALE_DY             ");
		sb.append("    , P.STR_CD                                                                                              AS STR_CD              ");
		sb.append("    , A.POS_NO                                                                                              AS POS_NO              ");
		sb.append("    , A.TRD_NO                                                                                              AS TRAN_NO             ");
		sb.append("    , A.SALE_PROD_SEQ                                                                                       AS TRAN_SEQ            ");
		sb.append("    , 'TR20'                                                                                                AS TRAN_ID             ");
		sb.append("    , A.SALE_EMP_NO                                                                                         AS SALE_EMP_NO         ");
		sb.append("    , A.TRD_TM                                                                                              AS TRAN_TM             ");
		sb.append("    , A.CANCEL_FG                                                                                           AS CNCL_DIV_CD         ");
		sb.append("    , DECODE(A.SALE_TYP_FG,'11','11','00')                                                                  AS SALE_FORM_CD        ");
		sb.append("    , A.SEL_CANCEL_FG                                                                                       AS APPOINT_CNCL_DIV_CD ");
		sb.append("    , A.SRCMK_CD                                                                                            AS SALE_BCD            ");
		sb.append("    , A.SRCMK_CD                                                                                            AS SRCMK_CD            ");
		sb.append("    , A.PROD_CD                                                                                             AS PROD_CD             ");
		sb.append("    , A.REP_CD                                                                                              AS REP_PROD_CD         ");
		sb.append("    , NVL(R.L1_CD,'999')                                                                                    AS L1_CD               ");
		sb.append("    , NVL(R.L2_CD,'9999')                                                                                   AS L2_CD               ");
		sb.append("    , NVL(R.L3_CD,'99999')                                                                                  AS L3_CD               ");
		sb.append("    , NVL(Q.L4_CD,'999999')                                                                                 AS L4_CD               ");
		sb.append("    , A.VEN_CD                                                                                              AS VEN_CD              ");
		sb.append("    , A.NOPROD_FG                                                                                           AS NPROD_DIV_CD        ");
		sb.append("    , A.TRD_TYP_FG                                                                                          AS TRAN_FORM_CD        ");
		sb.append("    , A.TAX_FG                                                                                              AS VAT_CD              ");
		sb.append("    , DECODE(A.TAX_FG, '2', 10, 0)                                                                          AS VAT_RT              ");
		sb.append("    , A.SALE_QTY                                                                                            AS SALE_QTY            ");
		sb.append("    , A.WGCNT                                                                                               AS SALE_WG             ");
		sb.append("    , A.CONV_RT                                                                                             AS CONV_QTY            ");
		sb.append("    , NVL(A.CURR_SALE_PRC + DECODE(NVL(A.SALE_QTY,0), 0, 0,  NVL(A.WARR_AMT,0) / A.SALE_QTY),0)             AS SALE_PRC            ");
		sb.append("    , NVL(A.SALE_PRC      + DECODE(NVL(A.SALE_QTY,0), 0, 0,  NVL(A.WARR_AMT,0) / A.SALE_QTY),0)             AS NRML_SPRC           ");
		sb.append("    , NVL(A.SALE_AMT,0) + NVL(A.WARR_AMT,0)                                                                 AS SALE_AMT            ");
		sb.append("    , NVL(A.NTAX_SALE_AMT + A.WARR_AMT,0)                                                                   AS EVAT_SALE_AMT       ");
		sb.append("    , NVL(A.DC_AMT,0)                                                                                       AS SALE_DC_AMT         ");
		sb.append("    , NVL(A.SALE_AMT - A.DC_AMT + A.WARR_AMT,0)                                                             AS NET_SALE_AMT        ");
		sb.append("    , NVL(A.NTAX_SALE_PRC  + A.WARR_AMT,0)                                                                  AS EVAT_NET_SALE_AMT   ");
		sb.append("    , NVL((A.SALE_AMT - A.DC_AMT + A.WARR_AMT) - (A.NTAX_SALE_PRC  + A.WARR_AMT),0)                         AS SALE_VAT_AMT        ");
		sb.append("    , NVL(A.PROFIT_AMT,0)                                                                                   AS MD_SALE_PRFT_AMT    ");
		sb.append("    , NVL(A.BUY_PRC_AMT + A.WARR_AMT,0)                                                                     AS MD_SALE_BPRC_AMT    ");
		sb.append("    , NVL(A.PROFIT_RT,0)                                                                                    AS MD_SALE_PRFT_RT     ");
		sb.append("    , NVL(A.PROFIT_AMT_2,0)                                                                                 AS STR_PRFT_AMT        ");
		sb.append("    , NVL(A.BUY_PRC_AMT_2 + A.WARR_AMT,0)                                                                   AS STR_BPRC_AMT        ");
		sb.append("    , NVL(A.PROFIT_RT_2,0)                                                                                  AS STR_PRFT_RT         ");
		sb.append("    , A.PROD_PAT_FG                                                                                         AS PROD_PTTN_DIV_CD    ");
		sb.append("    , A.DELI_FG                                                                                             AS TRUSTEE_DLV_DIV_CD  ");
		sb.append("    , A.WGCNT_FG                                                                                            AS WGCNT_DIV_CD        ");
		sb.append("    , A.NEW_PRC_FG                                                                                          AS NEW_PRC_DIV_CD      ");
		sb.append("    , '00'                                                                                                  AS UPDN_DIV_CD         ");
		sb.append("    , NVL(A.DC_AMT + A.CMSCP_AMT + A.HOMECP_AMT + DECODE (SUBSTRB(A.CARDCP_CD,1,1),'8', A.CARDCP_AMT,0),0)  AS DC_AMT              ");
		sb.append("    , NVL(A.WARR_AMT,0)                                                                                     AS BT_DEPOSIT_AMT      ");
		sb.append("    , '1'                                                                                                   AS SEARCH_DIV_CD       ");
		sb.append("    , '1'                                                                                                   AS INPUT_DIV_CD        ");
		sb.append("    , A.TRD_TM                                                                                              AS SCAN_TM             ");
		sb.append("    , ''                                                                                                    AS EVT_CD              ");
		sb.append("    , ''                                                                                                    AS EVT_COND_NO         ");
		sb.append("  FROM  SUPERNIS.SALE_PROD  A                                                                                                      ");
		sb.append(" INNER  JOIN USCM.CG_MIG_STR_NEW_OLD        P ON A.STR_CD  = P.OLD_STR_CD                                                          ");
		sb.append(" LEFT OUTER JOIN USCM.CG_PROD_COMM          Q ON A.PROD_CD = Q.PROD_CD                                                             ");
		sb.append(" LEFT OUTER JOIN USCM.VW_CG_CAT             R ON Q.L4_CD   = R.L4_CD                                                               ");
		sb.append(" WHERE  A.SALE_DY = ?                                                                                                              ");
		sb.append("   AND  A.SALE_TYP_FG <> '21'                                                                                                      ");

		Aaa aaa = new Aaa(sb.toString());
		aaa.sale_prod();
		

	}

	private void sale_prod() {
		try {
			// Statement를 가져온다.
			stmt = conn.createStatement();
			// SQL문을 실행한다.
			rs = stmt.executeQuery(query);
			while (rs.next()) { 
				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String job = rs.getString(3);
				String mgr = rs.getString(4);
				String hiredate = rs.getString(5);
				String sal = rs.getString(6);
				String comm = rs.getString(7);
				String depno = rs.getString(8);
				// 결과를 출력한다.
				System.out.println( 
					empno + " : " + ename + " : " + job + " : " + mgr
					+ " : " + hiredate + " : " + sal + " : " + comm + " : "
				+ depno); 
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try {
				// ResultSet를 닫는다.
				rs.close();
				// Statement를 닫는다.
				stmt.close();
				// Connection를 닫는다.
				conn.close();
			} catch ( SQLException e ) {}
		}		
	}

}
