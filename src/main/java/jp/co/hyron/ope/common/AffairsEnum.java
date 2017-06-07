package jp.co.hyron.ope.common;

public enum AffairsEnum {
	在職証明("Z"),
	収入証明("S");
    
  private String label;

  AffairsEnum(String label){
    this.label = label;
  }
  
  public String getlabel(){
    return this.label;
  }
  
  public static void main(String[] args){
    for(AffairsEnum aff : AffairsEnum.values()){
      System.out.println("[" + aff.ordinal() + "]," + aff.name() + "," + aff.getlabel());
    }
  }
}
