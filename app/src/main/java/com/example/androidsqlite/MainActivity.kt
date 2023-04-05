package com.example.androidsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val DB_NAME = "sqlite.sql"
    val DB_VERSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val helper = SqliteHelper(this, DB_NAME, DB_VERSION)
        val adapter = RecyclerAdapter()

        val memos = helper.selectMemo()
        adapter.listData.addAll(memos)

        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener {
            val content = binding.editMemo.text.toString()
            if (content.isNotEmpty()) {
                val memo = Memo(null, content, System.currentTimeMillis())
                helper.insertMemo(memo)
                // 기존 작성글 삭제
                binding.editMemo.setText("")
                // 목록 갱신
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectMemo())
                adapter.notifyDataSetChanged()
            }
        }

    }
}