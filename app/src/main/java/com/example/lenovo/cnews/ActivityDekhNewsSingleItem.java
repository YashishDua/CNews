package com.example.lenovo.cnews;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ActivityDekhNewsSingleItem extends AppCompatActivity implements DownloadDescriptionTask.DownloadDescriptionTaskInterface {
    String url = "http://www.dekhnews.com/feed/";
    String Description ,Title ,Publisher , ImageURL ,ID;
    HandlerDekhNewsDescription handlerDekhNewsDescription;
    TextView textviewDescription , textviewTitle , textviewPublisher;
    ImageView imageview ;
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    Button shareButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dekh_news_single_item);
        textviewDescription = (TextView)findViewById(R.id.textview_description_single);
        textviewTitle = (TextView)findViewById(R.id.textview_title_single) ;
        textviewPublisher  =(TextView)findViewById(R.id.textview_publisher);
        shareButton = (ShareButton)findViewById(R.id.button_share_facebook);
        shareButton.setEnabled(true);

        imageview = (ImageView)findViewById(R.id.imageview);
        DisplayMetrics displaymetrics ;
        displaymetrics = getBaseContext().getApplicationContext().getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        imageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/3));

        ID  = getIntent().getStringExtra("ID");
        new DownloadDescriptionTask(this , ID).execute(url);
        handlerDekhNewsDescription = new HandlerDekhNewsDescription(ID);



        Log.i("Adapter","Id passing to download task "+ ID);
        Title = getIntent().getStringExtra("TITLE");
        Publisher = getIntent().getStringExtra("PUBLISHER");
        ImageURL = getIntent().getStringExtra("URL");

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(ActivityDekhNewsSingleItem.this,"Sharing..",Toast.LENGTH_SHORT).show();
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(String.valueOf(textviewTitle.getText()))
                            .setContentDescription(String.valueOf(textviewDescription.getText()))
                            .setContentUrl(Uri.parse("http://dekhnews.com"))
                            .setImageUrl(Uri.parse(ImageURL))
                            .build();

                    shareDialog.show(linkContent);
                }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void dataprocessed() throws IOException {
        Description = handlerDekhNewsDescription.getDescription();
        textviewDescription.setText(Description);
        textviewTitle.setText(Title);
        textviewPublisher.setText(Publisher);
        Log.i("Picasso","Image URl"+ ImageURL);

        Picasso.Builder builder = new Picasso.Builder(ActivityDekhNewsSingleItem.this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Toast.makeText(ActivityDekhNewsSingleItem.this,"Failed!"+exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.build().load(ImageURL).into(imageview);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
