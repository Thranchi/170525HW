package kr.blogspot.httpcarelesssandbox.a170525hw;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class work extends AppCompatActivity {

    String browserName="";
    int isthistaskstillrunning=0;
    int iconStack=0;
    bottomText bottomText;
    ImageView icon;
    Button button;
    EditText et;
    TextView bottomtext;
    boolean keepgo=true;
    int picturenumber=0;
    long waitsec=3000;
    int totalsec=0;
    int selecsec=0;
    int threadsec=0;

    boolean calmdown=false;
    boolean isitstop=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        icon=(ImageView)findViewById(R.id.imageView);
        et=(EditText)findViewById(R.id.timer);
        button=(Button)findViewById(R.id.button);
        bottomtext=(TextView)findViewById(R.id.bottomtimer);
    }

    public void onClick(View v){
        if(v.getId()==R.id.imageView){
            if(calmdown){
                icon.setImageResource(R.drawable.safari);
                picturenumber=4;
                bottomtext.setText("시작부터 "+0+"초");
                calmdown=false;
                isthistaskstillrunning = 1;
            }
            else {
                //가동중
                if (isthistaskstillrunning == 1) {
                    isitstop = true;
                    isthistaskstillrunning = 2;
                }
                //가동전
                else if (isthistaskstillrunning == 0) {
                    bottomText = new bottomText();
                    bottomText.execute();
                    isthistaskstillrunning = 1;
                }
            }
        }
        else if(v.getId()==R.id.button){
                et.setHint("3");
                icon.setImageResource(R.drawable.start);
                calmdown=true;
                isitstop=false;
                selecsec=0;
                totalsec=0;
            iconStack=0;
            threadsec=0;
            waitsec=0;
        }

    }


    //bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
    class  bottomText extends AsyncTask<Integer,Integer,Void> {
        public void pick(int key){
            if(key==0){
                icon.setImageResource(R.drawable.chrome);
            }
            else if(key==1){
                icon.setImageResource(R.drawable.firefox);
            }
            else if(key==2){
                icon.setImageResource(R.drawable.iex);
            }
            else if(key==3){
                icon.setImageResource(R.drawable.opera);
            }
            else if(key==4){
                icon.setImageResource(R.drawable.safari);
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            icon.setImageResource(R.drawable.safari);
            picturenumber=4;
            bottomtext.setText("시작부터 "+totalsec+"초");
        }

        @Override
        protected Void doInBackground(Integer... params) {
                //isCancelled가 트루이면 온 프로그레스업데이트로 안간다 바로 캔슬로 간다
                if(isCancelled()==true) return null;
            while (keepgo) {
                try {
                    Thread.sleep(1000);
                    publishProgress(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(calmdown) {
                bottomtext.setTextColor(Color.BLACK);
                bottomtext.setText("처음 화면");
            }
            else{
                if (isitstop) {
                    bottomtext.setTextColor(Color.BLACK);
                    browserName = letusseewhattheychoose(picturenumber);
                    selecsec = values[0] + selecsec;
                    bottomtext.setText(browserName + " 선택 " + selecsec + "초");

                } else {

                    //1마다 초제한 불러오기
                    if (et.getText().toString().equals(""))
                        threadsec = 3;
                    else
                        threadsec = Integer.parseInt(et.getText().toString());

                    waitsec = threadsec;

                    //초제한마다 그림 바꾸기
                    iconStack = iconStack + values[0];
                    if (iconStack % waitsec == 0) {
                        picturenumber++;
                        picturenumber = picturenumber % 5;
                        pick(picturenumber);
                    }


                    totalsec = values[0] + totalsec;
                    bottomtext.setText("시작부터 " + totalsec + "초");
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {

            super.onCancelled();

        }

        public String letusseewhattheychoose(int key){
            key=key%5;
            if(key==0){
                return "크롬";
            }
            else if(key==1){
                return "파이어폭스";
            }
            else if(key==2){
                return "인터넷 익스플로러";
            }
            else if(key==3){
                return "오페라";
            }
            else if(key==4){
                return "사파리";
            }
            return "뭔가 오류가";
        }
    }
}
