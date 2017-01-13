package cz.muni.fi.pa165.sportsClub.enums;

/**
 * Represents age category
 * 
 * @author Andrej 410433
 */
public enum Category {
	MEN(200,17), U19(19,15), U17(17,13), U15(15,11), U13(13,0);

	private int upperAgeLimit;
	private int bottomAgeLimit;

	private Category(int upperAgeLimit, int bottomAgeLimit){
		this.upperAgeLimit = upperAgeLimit;
		this.bottomAgeLimit = bottomAgeLimit;
	}

	public int getUpperAgeLimit(){
		return upperAgeLimit;
	}

	public int getBottomAgeLimit(){
		return bottomAgeLimit;
	}

}

