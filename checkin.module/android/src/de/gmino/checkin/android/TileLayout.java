package de.gmino.checkin.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TileLayout extends ViewGroup {
	
	private int columnWidth = 200;
	private int rowHeight = 200;

	public TileLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TileLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TileLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		
		int columns = width / columnWidth;
		if(columns == 0)
			columns = 1;
		
		final int lp = (width - columns * columnWidth) / 2;
		
		final int count = getChildCount();
		System.out.println("Measured " + count + " buttons.");
		int row = 0;
		int column = 0;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			int x = lp + column * columnWidth;
			int y = row * rowHeight;
			int span = 1;
			if(column < columns - 1 && Math.random() < 0.3)
				span = 2;
			XyLayoutParams params = (XyLayoutParams)child.getLayoutParams();
			params.x = x;
			params.y = y;
			params.width = columnWidth * span;
			params.height = rowHeight;
			column += span;
			if(column == columns)
			{
				column = 0;
				row ++;
			}
		}

		int height = row * rowHeight;
		
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int w = r - l;
		int columns = w / columnWidth;
		if(columns == 0)
			columns = 1;
		
		final int lp = (w - columns * columnWidth) / 2;
		
		final int count = getChildCount();
		System.out.println("Layed out" + count + " buttons.");
		int row = 0;
		int column = 0;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			XyLayoutParams params = (XyLayoutParams)child.getLayoutParams();
			child.layout(params.x, params.y, params.x + params.width, params.y + params.height);
		}
	}
	
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof XyLayoutParams;
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new XyLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new XyLayoutParams(getContext(), attrs);
	}
	
	@Override
	protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
		return new XyLayoutParams(p.width, p.height);
	}
	
	public static class XyLayoutParams extends ViewGroup.LayoutParams {
		int x = 0;
		int y = 0;
		
		public XyLayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public XyLayoutParams(int w, int h) {
			super(w, h);
		}
	}
}
