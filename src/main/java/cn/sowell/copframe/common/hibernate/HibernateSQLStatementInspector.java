package cn.sowell.copframe.common.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public class HibernateSQLStatementInspector implements StatementInspector{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430587750699666204L;

	Logger logger = Logger.getLogger(HibernateSQLStatementInspector.class);
	
	@Override
	public String inspect(String sql) {
		if(!sql.equalsIgnoreCase("select o.id from t_order_kanteen o where o.c_pay_expired_time < ? and o.c_pay_time is null and o.c_canceled_status is null")){
			logger.debug(sql);
		}
		return null;
	}

}
