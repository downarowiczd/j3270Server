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
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param content
	 * @param write
	 * @param intense
	 * @param name
	 */
	public Field(int row, int col, String content, boolean write, boolean intense, String name) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
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
	
	
	
	
}
