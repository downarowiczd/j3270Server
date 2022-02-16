/**
Copyright Dominik Downarowicz 2022
https://github.com/HealPoint/j3270Server
Based on https://github.com/racingmars/go3270
LICENSE in the project root for license information

**/
package at.downardo.j3270Server;

public class Field {
	
	private int Row;
	private int Col;
	private String Content;
	private boolean Write;
	private boolean Intense;
	private String Name;
	
	
	public Field(int row, int col, String content, boolean write, boolean intense, String name) {
		Row = row;
		Col = col;
		Content = content;
		Write = write;
		Intense = intense;
		Name = name;
	}
	
	public Field(int row, int col, boolean intense, String content) {
		Row = row;
		Col = col;
		Content = content;
		Write = false;
		Intense = intense;
		Name = "";
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
