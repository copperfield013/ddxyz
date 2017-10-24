package cn.sowell.ddxyz.model.kanteen;

import java.util.HashMap;
import java.util.Map;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;

@SuppressWarnings("serial")
public interface KanteenConstants {

	Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>(){
		{
			put(PlainKanteenOrder.STATUS_DEFAULT, "未支付");
			put(PlainKanteenOrder.STATUS_PAIED, "已支付");
		}
	};

}
