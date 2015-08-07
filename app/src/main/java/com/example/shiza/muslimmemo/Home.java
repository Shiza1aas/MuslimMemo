package com.example.shiza.muslimmemo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment

{

    String url = "https://muslimmemo.com";
    Elements heading;
    Elements headingLink;
    Elements headingSummary;
    Elements author;
    Elements authorLinks;
    Elements category;
    Elements categoryLinks;
    Elements published;
    Elements next;
    ProgressBar progressBar;
    ListView listView;
    CustomAdapter customAdapter;

    public Home() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.pbHeaderProgress);
        listView = (ListView)view.findViewById(R.id.listView);

        Title title = new Title();
        title.execute(url);
        Log.d("Home","Hello from home");
        return view;

    }

     private class Title extends AsyncTask<String, Void, Wrapper> {
        Wrapper w = new Wrapper();

//        public Title(Context context)
//        {
//            mContext = context;
//        }
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Wrapper doInBackground(String... params) {

            try {

                Document document = Jsoup.connect(params[0]).get();

                heading = document.getElementsByClass("entry-title");
                headingLink = document.select("h1.entry-title > a[href]");
                headingSummary = document.getElementsByClass("entry-summary");
                author = document.getElementsByClass("author");
                authorLinks = document.select("span.author > a[href]");
                category = document.getElementsByClass("cat-links");
                categoryLinks = document.select("span.cat-links > a[href]");
                published = document.getElementsByClass("published");
                next = document.select("a.next");


                for ( Element headings : heading)
                {
                    w.heading.add(headings.text());
                }
                for ( Element headingLinks : headingLink)
                {
                    w.headingLinks.add(headingLinks.text());
                }

                for ( Element headingSummarys : headingSummary)
                {
                    w.headingSummary.add(headingSummarys.text());
                }

                for ( Element authors : author)
                {
                    w.author.add(authors.text());
                }
                for ( Element publisheds : published)
                {
                    w.published.add(publisheds.text());
                }

                if ( !next.isEmpty() )
                {
                    w.nextUrl = next.attr("href");
                }
                else
                {
                    w.nextUrl = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return w;
        }

        protected void onPostExecute(Wrapper result)
        {
            if ( result.nextUrl != null)
            {
                if ( result.nextUrl.contains("page/2"))
                {
                    customAdapter = new CustomAdapter(getActivity(), result.heading, result.headingSummary,
                            result.author, result.published);
                    listView.setAdapter(customAdapter);
                }
                else
                {
                    customAdapter.addItem(result.heading, result.headingSummary,
                            result.author, result.published);
                }
                Title title = new Title();
                title.execute(result.nextUrl);
                Log.d("tag","calling again" + result.nextUrl);
            }
            else
            {
                customAdapter.addItem(result.heading, result.headingSummary,
                        result.author, result.published);
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "is null");
            }


        }
    }

}
