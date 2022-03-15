/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;


/**
 * Field is a field on the 3270 screen
 * @author downarowiczd
 *
 */
public class Field {
	/**
	 * Row is the row, 0-based, thatthe field attribute character should
	 * begin at. This library currently only support 24 rows,
	 * so Row must be 0-23
	 */
	public int Row;
	/**
	 * Col is the column, 0-based, that the field attribute character should
	 * begin at. This library currently only supposed 80 columns, so Column
	 * must be 0-79
	 */
	public int Col;
	/**
	 * Text ist the contet of the field to display.
	 */
	public String Content;
	/**
	 * Write allows the use to edit the value of the field
	 */
	public boolean Write;
	/**
	 * Intense indicates this field should be displayed with high intensity
	 */
	public boolean Intense;
	/**
	 * Name is the name of this field, which is use to get the user-entered
	 * data. All writeable fields on a screen must have a unique name.
	 */
	public String Name;
	
	public boolean Hidden;
	
	public Colour colour;
	
	public Highlight highlight;
	
	
	/**
	 * KeepSpaces will prevent the strings.TrimSpace() function from being
	 * called on the field value. Generally you want leading and trailing
	 * spaces trimmed from fields in 3270 before processing, but if you are
	 * building a whitespace-sensitive application, you can ask for the
	 * original, un-trimmed value for a field by setting this to true.
	 */
	public boolean KeepSpaces;
	
	
	public static enum Colour {
		DefaultColour(0),
		Blue(0xf1),
		Red(0xf2),
		Pink(0xf3),
		Green(0xf4),
		Turquosie(0xf5),
		Yellow(0xf6),
		White(0xf7);
		
		public int value;
		private Colour(int value) {
			this.value = value;
		}
		
	}

	public static enum Highlight {
		DefaultHighlight(0),
		Blink(0xf1),
		ReverseVideo(0xf2),
		Underscore(0xf4);
		
		
		public int value;
		private Highlight(int value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @param content
	 * @param write
	 * @param intense
	 * @param hidden
	 * @param name
	 */
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name, Colour colour, Highlight highlight) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = colour;
		this.highlight = highlight;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name, Colour colour, Highlight highlight, boolean keepSpaces) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = colour;
		this.highlight = highlight;
		this.KeepSpaces = keepSpaces;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, Colour colour, Highlight highlight) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = "";
		Hidden = hidden;
		this.colour = colour;
		this.highlight = highlight;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, Colour colour) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = "";
		Hidden = hidden;
		this.colour = colour;
		highlight = Highlight.DefaultHighlight;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name, Colour colour) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = colour;
		highlight = Highlight.DefaultHighlight;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name, Colour colour, boolean keepSpaces) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = colour;
		highlight = Highlight.DefaultHighlight;
		KeepSpaces = keepSpaces;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = Colour.DefaultColour;
		highlight = Highlight.DefaultHighlight;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden, String name, boolean keepSpaces) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
		Hidden = hidden;
		this.colour = Colour.DefaultColour;
		highlight = Highlight.DefaultHighlight;
		KeepSpaces = keepSpaces;
	}
	
	public Field(int row, int col, String content, boolean write, boolean intense, boolean hidden) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = "";
		Hidden = hidden;
		this.colour = Colour.DefaultColour;
		highlight = Highlight.DefaultHighlight;
	}
	
	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return Hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		Hidden = hidden;
	}
	
	/**
	 * @return the col
	 */
	public int getCol() {
		return Col;
	}
	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		Col = col;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		Content = content;
	}
	/**
	 * @return the write
	 */
	public boolean isWrite() {
		return Write;
	}
	/**
	 * @param write the write to set
	 */
	public void setWrite(boolean write) {
		Write = write;
	}
	/**
	 * @return the intense
	 */
	public boolean isIntense() {
		return Intense;
	}
	/**
	 * @param intense the intense to set
	 */
	public void setIntense(boolean intense) {
		Intense = intense;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return Row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		Row = row;
	}

	/**
	 * @return the colour
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	/**
	 * @return the highlight
	 */
	public Highlight getHighlight() {
		return highlight;
	}

	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(Highlight highlight) {
		this.highlight = highlight;
	}

	/**
	 * @return the keepSpaces
	 */
	public boolean isKeepSpaces() {
		return KeepSpaces;
	}

	/**
	 * @param keepSpaces the keepSpaces to set
	 */
	public void setKeepSpaces(boolean keepSpaces) {
		KeepSpaces = keepSpaces;
	}
	
	
	
	
}
