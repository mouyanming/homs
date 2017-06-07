package jp.co.hyron.ope.common;

public enum AffairStatusEnum {
	初期状態("0"),
	処理中("1"),
	承認済("2"),
	未承認("3"),
	差戻("8"),
	処理済("9"),
    ;

	private String label;

	AffairStatusEnum(String label){
		this.label = label;
	}
  
	public String getlabel(){
		return this.label;
	}
  
	public static void main(String[] args){
		for(AffairStatusEnum aff : AffairStatusEnum.values()){
			System.out.println("[" + aff.ordinal() + "]," + aff.name() + "," + aff.getlabel());
		}
	}

}
