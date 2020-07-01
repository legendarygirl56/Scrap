package com.ayushidoshi56.scrap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class MainActivity : AppCompatActivity() {
    var imageUrl:String=""
    val rankslist = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            Contributionextract()
            Rankings()
        }
    }

    private suspend fun Rankings() {
        val job=GlobalScope.launch(Dispatchers.Default) {
            var r:String="RANKS IN CONTESTS"
            r=r+"\n"
            val url2="https://codeforces.com/contests/with/Um_nik"
            val doc2: Document = Jsoup.connect(url2).get()
            val table=doc2.select("table").eq(5)
            for (row in table.select("tr")) {
                    val tds: String = row.select("td").eq(2).text()
                    r+=tds
                    r+="\n"
                    tv2.text=r
                  // rankslist.add((tds.toInt()))
                }
            tv2.text=r
            }

        job.join()
    }

    private suspend fun Contributionextract() {
        val job=GlobalScope.launch(Dispatchers.Default) {
            var s:String=""
            val url="https://codeforces.com/profile/Um_nik"
            val doc: Document = Jsoup.connect(url).get()

            val profile=doc.select("div.title-photo")

            val div:Elements=doc.select("div.info")
            val se: String = profile.select("img").eq(0).attr("src")
            imageUrl="https:" + se

            val tag: String? =div.select("a").attr("title")
            s+=("Profile:- " + tag)
            val sec:Elements=div.select("ul")
            for(i in 0..4)
            {
                val register:Elements=sec.select("li").eq(i)
                val months:String=register.text()
                s+="\n"
                s+=months
            }
            tv.text=s
        }
        job.join()
        Picasso.get().load(imageUrl).into(imgv)
    }
}