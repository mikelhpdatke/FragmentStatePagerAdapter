package mikel.tom.fragmentstatepageradapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.mklimek.frameviedoview.FrameVideoView;
import com.mklimek.frameviedoview.FrameVideoViewListener;

public class VideoFragment extends Fragment {
    int fragVal;
    int state = 0;
    FrameVideoView videoView;
    MediaPlayer mediaPlayer;
    Context mContext;
    static VideoFragment init(int val) {
        VideoFragment truitonFrag = new VideoFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
        Log.e("Debug", (getArguments() == null ? " null" : "non-null"));
        Log.e("Debug", Integer.toString(fragVal));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_video, container,
                false);
        View tv = layoutView.findViewById(R.id.text);
        ((TextView) tv).setText("Video Fragment #" + fragVal);

        videoView = (FrameVideoView) layoutView.findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(mContext, false);
        mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);


        String uri = "android.resource://" + mContext.getPackageName() + "/" + R.raw.vid2;
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(Uri.parse(uri).getPath(),
                MediaStore.Images.Thumbnails.MINI_KIND);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
        // Setup a Video View + PlaceHolder
        videoView.setup(Uri.parse(uri), getResources().getColor(R.color.colorPrimary));

        //Log.e("DebugImg",Uri.parse(uri).getPath());

        videoView.setFrameVideoViewListener(new FrameVideoViewListener() {
            @Override
            public void mediaPlayerPrepared(MediaPlayer mediaPlayer) {
                VideoFragment.this.mediaPlayer = mediaPlayer;
                mediaPlayer.start();
            }

            @Override
            public void mediaPlayerPrepareFailed(MediaPlayer mediaPlayer, String s) {

            }
        });
        return layoutView;
    }
}
