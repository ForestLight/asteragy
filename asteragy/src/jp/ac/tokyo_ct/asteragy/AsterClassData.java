package jp.ac.tokyo_ct.asteragy;

public class AsterClassData {

	public final static String[] className = {
		"サン",
		"スター",
		"マーキュリー",
		"ヴィーナス",
		"アース",
		"マーズ",
		"サターン",
		"ウラヌス",
		"ネプチューン",
		"プルート"
	};
	
	public final static String[] commandName = {
		"クラスチェンジ",
		"スワップ",
		"クイックタイム",
		"テンプテーション",
		"イージス",
		"フレアスター",
		"ローテーション",
		"ロングレンジスワップ",
		"スターライトストリーム",
		"ルインクラスト"
	};	
	
	public final static String[] commandExplain = {
		"アステル1体にクラスを与える",
		"二つの隣り合ったアステルを入れ替える",
		"行動済ユニット1体を行動可能状態にする",
		"敵ユニット1体を奪い取る",
		"レンジ内の味方ユニットは次のターンの間対象にとられない",
		"アステル1個を破壊する(サンは選べない)",
		"敵ユニット1体を破壊する",
		"リング部分を右回りにローテーション",
		"二つのアステルを入れ替える",
		"自分とアステル1個を入れ替える",
		"レンジ内のアステルを全て破壊する"	
	};
	
	public final static int[] classCost = {
		0,2,8,7,6,8,7,9,10,11,11
	};
	
	public final static int[] commandCost = {
		0,0,4,7,1,4,1,2,1,2,8
	};
	
	public final static int[] actionNum ={
		2,2,1,1,1,1,1,1,1,1,1
	};
	
}
