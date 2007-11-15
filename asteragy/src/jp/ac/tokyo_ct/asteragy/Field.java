package jp.ac.tokyo_ct.asteragy;

class Field {
	public Field() {
		super();
	}
	
	/**
	 * 指示された行動を行う
	 * @param a
	 * @return
	 */
	public boolean act(Action a) {
		//行動を起こす
		//...
		
		GameCanvas c = Game.getInstance().getCanvas();
		//cへフィールドの描画を依頼
		return false;
	}
	
	private Aster[][] field;
}
