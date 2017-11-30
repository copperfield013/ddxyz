package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;


public class KanteenMenuOrderStat {
	//待完成订单
	private Integer effective;
	//已完成订单
	private Integer completed;
	//已取消或已关闭订单
	private Integer canceled;
	//未领取订单
	private Integer missed;
	//已关闭的订单数
	private Integer closed;
	//总数
	private Integer totalCount;
	//已支付的总金额
	private Integer paiedAmount;
	//已确认但是未支付的总金额
	private Integer confirmedAmount;
	
	public Integer getEffective() {
		return effective;
	}
	public void setEffective(Integer effective) {
		this.effective = effective;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getCanceled() {
		return canceled;
	}
	public void setCanceled(Integer canceled) {
		this.canceled = canceled;
	}
	public Integer getMissed() {
		return missed;
	}
	public void setMissed(Integer missed) {
		this.missed = missed;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getClosed() {
		return closed;
	}
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
	public Integer getPaiedAmount() {
		return paiedAmount;
	}
	public void setPaiedAmount(Integer paiedAmount) {
		this.paiedAmount = paiedAmount;
	}
	public Integer getConfirmedAmount() {
		return confirmedAmount;
	}
	public void setConfirmedAmount(Integer confirmedAmount) {
		this.confirmedAmount = confirmedAmount;
	}
	
	
}
