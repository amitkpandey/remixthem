package fr.steren.remixthem.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RemixThemManualView extends View {

	/** the picture */
    private BitmapDrawable mDrawablePicture;
	
	/** Position of the eyes*/
	private Point mEye1;
	private Point mEye2;
	
	public RemixThemManualView(Context context, Bitmap bitmap) {
		super(context);
		mDrawablePicture = new BitmapDrawable(bitmap);
		Toast.makeText(this.getContext(), R.string.manual_pick_eye , Toast.LENGTH_SHORT).show();
	}

	@Override protected void onDraw(Canvas canvas) {
		if(mDrawablePicture != null) {
			
			int pictureWidth = this.getHeight() *  mDrawablePicture.getIntrinsicWidth() / mDrawablePicture.getIntrinsicHeight();
			int positionLeft = (this.getWidth() - pictureWidth) / 2 ;
		    int positionTop = 0;
		    int positionRight = positionLeft + pictureWidth ;
		    int positionBottom = this.getHeight();
			
		    mDrawablePicture.setBounds(positionLeft, positionTop, positionRight, positionBottom);
	       	mDrawablePicture.draw(canvas);
	       	
	       	//if eyes have been selected, print circles on them.
	       	if(mEye1 != null) {
				Paint paint = new Paint();
				paint.setARGB(180, 255, 255, 255);
			    canvas.drawCircle(mEye1.x, mEye1.y, 6, paint);
			    
			    if(mEye2 != null) {
				    canvas.drawCircle(mEye2.x, mEye2.y, 6, paint);
			    }
	       	}
	       	
		}
	}
	
    @Override public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	
    	if (action == MotionEvent.ACTION_DOWN) {
	    	Point touchedPoint = new Point( (int)event.getX(), (int)event.getY());
	    	
	    	if(mEye1 == null) {
	    		mEye1 = touchedPoint;
	    		Toast.makeText(this.getContext(), R.string.manual_first_eye , Toast.LENGTH_SHORT).show();
            	invalidate();
	    	} else {
	    		if(touchedPoint.x < mEye1.x) {
	    			Toast.makeText(this.getContext(), R.string.manual_not_valid_eye2 , Toast.LENGTH_SHORT).show();  			
	    		}else {
	    			mEye2 = touchedPoint;
	    			Toast.makeText(this.getContext(), R.string.manual_second_eye , Toast.LENGTH_SHORT).show();
                	invalidate();
	    		}
	    	}	    		
	    	return true;
    	}
    	return false;
    }
    /**
     * get the position of the center of the eyes
     * @return the center of the eyes in the Bitmap
     */
	public PointF getEyePosition() {
		PointF center = new PointF( (float)(((mEye1.x + mEye2.x) / 2.f * mDrawablePicture.getBitmap().getHeight())) / mDrawablePicture.getBounds().height(),
									(float)(((mEye1.y + mEye2.y) / 2.f * mDrawablePicture.getBitmap().getWidth())) / mDrawablePicture.getBounds().width() );
		return center;
	}
    
	/**
     * get the distance between the 2 eyes
     * @return the distance between the 2 eyes
     */
	public float getEyeDistance() {
		float distance = ((float) ((mEye2.x - mEye1.x) * mDrawablePicture.getBitmap().getWidth())) / mDrawablePicture.getBounds().width()  ; 		
		return distance;
	}
	
	public Bitmap getBitmap() {
		return mDrawablePicture.getBitmap();
	}
	
	public boolean isReady() {
		if(mEye1 == null) { return false ; }
		else if(mEye2 == null) { return false; }
		return true;
	}
	
}
