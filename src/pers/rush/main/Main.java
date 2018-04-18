package pers.rush.main;

import pers.rush.graph.GraphFrame;

/**
 * @description 做一个简单的绘图工具，以CAD的方式操作，
 * 能放置直线、矩形、圆和文字，[OK]
 * 能选中图形，修改参数，如颜色等，[OK]
 * 能拖动图形和调整大小，[OK]
 * 可以保存和恢复。[OK]
 * @author Rush
 *
 */

public class Main {

	public static void main(String[] args) {
		new GraphFrame("无标题 - 画图");
	}

}
