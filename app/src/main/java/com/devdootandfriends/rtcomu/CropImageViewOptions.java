// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.devdootandfriends.rtcomu;

import android.util.Pair;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * The crop image view options that can be changed live.
 */
final class CropImageViewOptions {

    public ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;

    public CropImageView.CropShape cropShape = CropImageView.CropShape.RECTANGLE;

    public CropImageView.Guidelines guidelines = CropImageView.Guidelines.ON_TOUCH;

    public Pair<Integer, Integer> aspectRatio = new Pair<>(1, 1);

    public boolean fixAspectRatio;

    public boolean showCropOverlay;

    public boolean showProgressBar;
}
