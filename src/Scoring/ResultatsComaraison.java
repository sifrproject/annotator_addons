package Scoring;

public class ResultatsComaraison {
	public int NbAnnotExpert; 
	public int NbAnExpertDansAnAuto;
	public int ClassementEdansScore;
	public String ClassementEdansScoreDetails;
	public String ClassementEdansScoreDetailsF;
	public int ClassementEdansScoreF;
	public int ClassementEdansScoreFH;
	ResultatsComaraison (int NbAnnotExpert,
		 int NbAnExpertDansAnAuto,
		 int ClassementEdansScore,
		 String ClassementEdansScoreDetails,
		 int ClassementEdansScoreF,
		 String ClassementEdansScoreDetailsF,
		 int ClassementEdansScoreFH)
		 {
		        this.NbAnnotExpert=NbAnnotExpert;
				this.NbAnExpertDansAnAuto=NbAnExpertDansAnAuto;
				this.ClassementEdansScore=NbAnExpertDansAnAuto;
				 this.ClassementEdansScoreDetails= ClassementEdansScoreDetails;
				this.ClassementEdansScoreF=NbAnExpertDansAnAuto;
				this.ClassementEdansScoreDetailsF=ClassementEdansScoreDetailsF;
				this.ClassementEdansScoreFH=NbAnExpertDansAnAuto;
		 }
}
