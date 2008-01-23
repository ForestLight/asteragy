package jp.ac.tokyo_ct.asteragy;

public class AsterClassData {

	public final static String[] className = {
		"ｻﾝ",
		"ｽﾀｰ",
		"ﾏｰｷｭﾘｰ",
		"ｳﾞｨｰﾅｽ",
		"ｱｰｽ",
		"ﾏｰｽﾞ",
		"ｼﾞｭﾋﾟﾀｰ",
		"ｻﾀｰﾝ",
		"ｳﾗﾇｽ",
		"ﾈﾌﾟﾁｭｰﾝ",
		"ﾌﾟﾙｰﾄ",
		"ﾑｰﾝ"
	};
	
	public final static String[] commandName = {
		"クラスチェンジ",
		"スワップ",
		"クイックタイム",
		"テンプテーション",
		"サモンムーン",
		"フレアスター",
		"プロテクションシステム",
		"ローテーション",
		"ロングレンジスワップ",
		"スターライトストリーム",
		"ルインクラスト",
		"トータルイクリプス"
	};
	
	public final static String[] commandExplain = {
		"アステル1体にクラスを与える",
		"二つの隣り合ったアステルを入れ替える",
		"行動済ユニット1体を行動可能状態にする",
		"敵ユニット1体を奪い取る",
		"レンジ内にムーンを呼び出す",
		"アステル1個を破壊する(サンは選べない)",
		"敵ユニット1体を破壊する",
		"リング部分を右回りにローテーション",
		"二つのアステルを入れ替える",
		"自分とアステル1個を入れ替える",
		"レンジ内のアステルを全て破壊する",
		"レンジ内まで自分のサンを移動させる"
	};
	
	public final static int[] classCost = {
		0,2,8,7,6,8,7,9,10,11,11,0
	};
	
	public final static int[] commandCost = {
		0,0,4,7,6,4,1,2,1,2,8,8
	};
	
	public final static int[] actionNum ={
		2,2,1,1,1,1,1,1,1,1,1,1
	};
	
}
