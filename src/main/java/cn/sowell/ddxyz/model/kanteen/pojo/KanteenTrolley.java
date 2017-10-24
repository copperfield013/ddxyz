package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.List;

public class KanteenTrolley{
	private PlainKanteenTrolley plainTrolley;
	private List<KanteenTrolleyWares> validWares;
	private List<KanteenTrolleyWares> invalidWares;
	
	public Long getId(){
		if(plainTrolley != null){
			return plainTrolley.getId();
		}
		return null;
	}

	public Integer getTotalValidPrice(){
		Integer price = 0;
		if(validWares != null){
			for (KanteenTrolleyWares valid : validWares) {
				price += valid.getBasePrice() * valid.getCount();
			}
		}
		return price;
	}
	
	public Integer getTotalValidCount(){
		Integer count = 0;
		if(validWares != null){
			for (KanteenTrolleyWares valid : validWares) {
				count += valid.getCount();
			}
		}
		return count;
	}
	
	public PlainKanteenTrolley getPlainTrolley() {
		return plainTrolley;
	}

	public void setPlainTrolley(PlainKanteenTrolley plainTrolley) {
		this.plainTrolley = plainTrolley;
	}

	public List<KanteenTrolleyWares> getValidWares() {
		return validWares;
	}

	public void setValidWares(List<KanteenTrolleyWares> validWares) {
		this.validWares = validWares;
	}

	public List<KanteenTrolleyWares> getInvalidWares() {
		return invalidWares;
	}

	public void setInvalidWares(List<KanteenTrolleyWares> invalidWares) {
		this.invalidWares = invalidWares;
	}

}
