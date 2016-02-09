package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.UserPosition;
import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.Usuario;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Response;

public class RankingActivity extends AppCompatActivity {

    ListView listView;
    TextView texViewPos;
    TextView nickNameText;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listView = (ListView) findViewById(R.id.listViewRankingActivity);
        texViewPos = (TextView) findViewById(R.id.textViewPosActual);
        nickNameText = (TextView) findViewById(R.id.textViewNomUserNick);

        preferences = getSharedPreferences("kill_pato", MODE_PRIVATE);
        String nick = preferences.getString("nick", null);

        if(nick!=null){
            new GetPositionTask().execute(nick);
            nickNameText.setText("Hola! " + nick);
        }

        new ObtenerRankingTask().execute();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }




    public class GetPositionTask extends AsyncTask<String, Void, UserPosition> {

        @Override
        protected UserPosition doInBackground(String... params) {
            if (params != null) {
                Call<UserPosition> call = Servicio.instanciarServicio().obtenerPosicionActual(params[0]);
                Response<UserPosition> response = null;

                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch(java.lang.IllegalStateException e){
                    return null;
                }catch(Exception e){
                    return null;
                }
                return response.body();

            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(UserPosition usr) {
            super.onPostExecute(usr);
            if(usr!=null){
                if(usr.getPosition()==1){
                    texViewPos.setText("YOU ARE THE BOSS! eres: " + usr.getPosition() +" º" );
                }else if(usr.getPosition()==2){
                    texViewPos.setText("Estás a un paso de la cima! eres: " + usr.getPosition() +" º" );
                }else if(usr.getPosition() == 3){
                    texViewPos.setText("Estás en el podio, buen trabajo eres: " + usr.getPosition() +" º" );
                }else{
                    texViewPos.setText("Eres del montón, eres " + usr.getPosition() +" º" );
                }

            }else{
                texViewPos.setText("WTF? Aún no estás en el ranking!" );
            }

        }
    }

    public class ObtenerRankingTask extends AsyncTask<Void, Void, ArrayList<Usuario>> {

        @Override
        protected ArrayList<Usuario> doInBackground(Void... params) {

            Call<ArrayList<Usuario>> listCall = Servicio.instanciarServicio().obtenerRanking();
            Response<ArrayList<Usuario>> listResponse = null;

            try {
                listResponse = listCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return listResponse.body();
        }

        @Override
        protected void onPostExecute(ArrayList<Usuario> usuarios) {
            super.onPostExecute(usuarios);
            listView.setAdapter(new RankingAdapter(RankingActivity.this, usuarios));
        }

    }


}
