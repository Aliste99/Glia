package com.projects.thirtyseven.glue;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listOfTags;
    Ticket ticket;
    FirebaseDatabase firebase;
    DatabaseReference databaseReference;
    ArrayList<Ticket> listOfTickets;
    ArrayAdapter adapter;
    private ArrayList<Post> postList;
    LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private static final String TAG = "myLogs";
    private static final int PICK_PERMS_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        asNeeded();
        init();
        setListeners();


        listOfTickets = new ArrayList<>();
        adapter = new CustomTicketAdapter(this, R.layout.custom_list_item, listOfTickets);
        postList = new ArrayList<>();
        listOfTags.setAdapter(adapter);
        listOfTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TicketInfoDialog ticketInfoDialog = new TicketInfoDialog();
                Ticket ticket = (Ticket) parent.getItemAtPosition(position);
                String ticket_id = ticket.getId();

                Bundle args = new Bundle();
                args.putString("ticket_id", ticket_id);

                FragmentManager fragmentManager = getSupportFragmentManager();

                TicketInfoDialog ticketInfoDialog1 = TicketInfoDialog.addSomeString(ticket_id);
                ticketInfoDialog.setArguments(args);
                ticketInfoDialog.setString(ticket_id);
                ticketInfoDialog.setContext(MainActivity.this);
                ticketInfoDialog.show(getFragmentManager(), "info");
            }
        });

    }


    private void setListeners() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ticket = dataSnapshot.getValue(Ticket.class);
                listOfTickets.add(ticket);
                listOfTags.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //listView.append(String.valueOf(proxducts.getName()) + "\n");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                recreate();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ticket = dataSnapshot.getValue(Ticket.class);
                for (int i = 0; i < listOfTickets.size(); i++){
                    if (listOfTickets.get(i).getId() == ticket.getId()) {
                        listOfTickets.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                Toast.makeText(
                        MainActivity.this,
                        //R.string.success,
                        "success",
                        Toast.LENGTH_LONG).show();
                // updateUI();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(
                        MainActivity.this,
                        //R.string.cancel,
                        "on cancel",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Toast.makeText(
                        MainActivity.this,
                        //R.string.error,
                        "error",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void init() {
        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference("tickets");
        listOfTags = (ListView) findViewById(R.id.listOfTegs);
        fbLoginButton = (LoginButton) findViewById(R.id._fb_login);
    }

    private void asNeeded() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent goToNextActivity = new Intent(MainActivity.this, AddTicketActivity.class);
                startActivity(goToNextActivity);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_by) {
            return true;
        } else if (id == R.id.action_source) {
            return true;
        } else if (id == R.id.action_update_news) {
            getPostsInfo();
            saveToFireBase();
            return true;
        } else if (id == R.id.permissions) {
            Intent selectPermsIntent =
                    new Intent(MainActivity.this, PermissionsActivity.class);
            startActivityForResult(selectPermsIntent, PICK_PERMS_REQUEST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PERMS_REQUEST) {
            if (resultCode == RESULT_OK) {
                String[] readPermsArr = data
                        .getStringArrayExtra(PermissionsActivity.EXTRA_SELECTED_READ_PARAMS);
                String writePrivacy = data
                        .getStringExtra(PermissionsActivity.EXTRA_SELECTED_WRITE_PRIVACY);
                String[] publishPermsArr = data
                        .getStringArrayExtra(
                                PermissionsActivity.EXTRA_SELECTED_PUBLISH_PARAMS);

                fbLoginButton.clearPermissions();

                if (readPermsArr != null) {
                    if (readPermsArr.length > 0) {
                        fbLoginButton.setReadPermissions(readPermsArr);
                    }
                }

                if ((readPermsArr == null ||
                        readPermsArr.length == 0) &&
                        publishPermsArr != null) {
                    if (publishPermsArr.length > 0) {
                        fbLoginButton.setPublishPermissions(publishPermsArr);
                    }
                }
                // Set write privacy for the user
                if ((writePrivacy != null)) {
                    DefaultAudience audience;
                    if (DefaultAudience.EVERYONE.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.EVERYONE;
                    } else if (DefaultAudience.FRIENDS.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.FRIENDS;
                    } else {
                        audience = DefaultAudience.ONLY_ME;
                    }
                    fbLoginButton.setDefaultAudience(audience);
                }
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveToFireBase() {
        for (Post post : postList) {
            databaseReference.getRoot().child("posts").child(post.getId()).setValue(post);
        }
        new RefreshTask().execute();
    }

    public void refreshWeb() {
        FeedParser parser = new FeedParser();
        List<FeedParser.Entry> entries = null;
        InputStream input = null;
        try {
            input = new URL("https://kloop.kg/feed/atom/").openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            entries = parser.parse(input);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (FeedParser.Entry entry : entries) {
            String hashId = String.valueOf(entry.id.hashCode());
            databaseReference.getRoot().child("web").child(hashId).setValue(entry);
        }
    }

    private class RefreshTask extends AsyncTask<Integer, Integer, Integer> {
        protected Integer doInBackground(Integer... num) {

            refreshWeb();
            return 0;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Integer result) {

        }


    }

    private void getPostsInfo() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/41383354638/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // info.append("\n");
                        Log.d(TAG, "getPostsId oncompleted");
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            Log.d(TAG, jsonObject.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            Log.d(TAG, dataArray.toString());
                            if (dataArray.length() > 0) {
                                ArrayList<String> posts = new ArrayList<String>();
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject post = dataArray.getJSONObject(i);
                                    String id = post.getString("id");
                                    String url = "";
                                    try {
                                        url = post.getString("link");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception url error");
                                    }
                                    String name = "";
                                    try {
                                        name = post.getString("name");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception name error");
                                    }
                                    String message = "";
                                    try {
                                        message = post.getString("message");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception message error");
                                    }
                                    String shares = "0";
                                    try {
                                        shares = post.getJSONObject("shares").getString("count");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception shares error");
                                    }
                                    Log.d(TAG, "post id: " + id + "\n"
                                            + "url: " + url + "\n"
                                            + "name: " + name + "\n"
                                            + "message: " + message + "\n"
                                            + "shares count: " + shares + "\n"
                                            + "_______________________________________");
                                    Post postt = new Post(name, message, Integer.parseInt(shares), id, url, false);
                                    postList.add(postt);
                                    // adapter.notifyDataSetChanged();
                                    posts.add(id);
                                }
                                for (String id : posts) {
                                    getPostInsights(id);
                                }
                            } else {
                                Log.d(TAG, "data array is empty");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "JSON exception while getting posts", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "JSON exception while getting posts");
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Getting posts error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,message,name,picture,shares,link");
        parameters.putString("limit", "20");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getPostInsights(String postId) {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + postId + "/insights/post_impressions,post_impressions_unique,post_reactions_by_type_total",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            if (dataArray.length() > 0) {
                                JSONObject js = dataArray.getJSONObject(0);
                                String fullId = js.getString("id");
                                String postId = getPostId(fullId);


                                for (int i = 0; i < postList.size(); i++) {
                                    Post post = postList.get(i);
                                    if (postId.equals(post.getId())) {

                                        for (int k = 0; k < dataArray.length(); k++) {
                                            JSONObject metric = dataArray.getJSONObject(k);
                                            String metricName = metric.getString("name");
                                            switch (metricName) {
                                                case "post_impressions":
                                                    String value = metric.getJSONArray("values").getJSONObject(0).getString("value");
                                                    post.setReached_total(Integer.parseInt(value));
                                                    Log.d(TAG, "REACHED TOTAL : " + value);
                                                    break;
                                                case "post_impressions_unique":
                                                    String reached_unique = metric.getJSONArray("values").getJSONObject(0).getString("value");
                                                    post.setReached_unique(Integer.parseInt(reached_unique));
                                                    break;
                                                case "post_reactions_by_type_total":
                                                    JSONObject reactions = metric.getJSONArray("values").getJSONObject(0).getJSONObject("value");
                                                    post.setLikesCount(Integer.parseInt(reactions.getString("like")));
                                                    post.setLovesCount(Integer.parseInt(reactions.getString("love")));
                                                    post.setHahaCount(Integer.parseInt(reactions.getString("haha")));
                                                    post.setWowCount(Integer.parseInt(reactions.getString("wow")));
                                                    post.setSadCount(Integer.parseInt(reactions.getString("sorry")));
                                                    post.setAngryCount(Integer.parseInt(reactions.getString("anger")));
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }

                                        // adapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                Log.d(TAG, "post data array is empty");
                            }

                        } catch (JSONException e) {
                            Log.d(TAG, "post parsing json error");
                        }
                    }
                });

        request.executeAsync();
    }

    private String getPostId(String fullId) {
        char[] origin = fullId.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < origin.length; i++) {
            if (origin[i] != '/') {
                stringBuilder.append(origin[i]);
            } else break;

        }
        return stringBuilder.toString();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
