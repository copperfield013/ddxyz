package cn.sowell.ddxyz.model.kanteen.service.impl;

import java.util.Comparator;


public interface Orderable {
	Integer getOrder();

	Comparator<Orderable> COMPARATOR = new Comparator<Orderable>(){

			@Override
			public int compare(Orderable o1, Orderable o2) {
				if(o1 == null && o2 == null){
					return 0;
				}else if(o1 == null){
					return -1;
				}else if(o2 == null){
					return 1;
				}else{
					Integer
						order1 = o1.getOrder(),
						order2 = o2.getOrder();
					if(order1 == null && order2 == null){
						return 0;
					}else if(order1 == null){
						return -1;
					}else if(order2 == null){
						return 1;
					}else{
						return Integer.compare(order1, order2);
					}
				}
			}
			
		};
}
