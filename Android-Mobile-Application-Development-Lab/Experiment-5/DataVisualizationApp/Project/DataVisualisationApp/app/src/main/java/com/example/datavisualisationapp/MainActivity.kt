package com.example.datavisualisationapp

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity:AppCompatActivity(){

    lateinit var daySpin:Spinner
    lateinit var valueBox:EditText
    lateinit var addBtn:Button
    lateinit var barBox:LinearLayout
    lateinit var barScroll:View
    lateinit var lineBox:FrameLayout
    lateinit var pieBox:FrameLayout
    lateinit var toggle:MaterialButtonToggleGroup

    val week=arrayOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat")
    val data=mutableMapOf<String,Float>()
    val colors=intArrayOf(
        Color.parseColor("#ff7675"),
        Color.parseColor("#74b9ff"),
        Color.parseColor("#55efc4"),
        Color.parseColor("#ffeaa7"),
        Color.parseColor("#a29bfe"),
        Color.parseColor("#fab1a0"),
        Color.parseColor("#81ecec")
    )

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daySpin=findViewById(R.id.daySpin)
        valueBox=findViewById(R.id.valueBox)
        addBtn=findViewById(R.id.addBtn)
        barBox=findViewById(R.id.barBox)
        barScroll=findViewById(R.id.barScroll)
        lineBox=findViewById(R.id.lineBox)
        pieBox=findViewById(R.id.pieBox)
        toggle=findViewById(R.id.toggle)

        week.forEach{data[it]=0f}

        val ad=ArrayAdapter(this,android.R.layout.simple_spinner_item,week)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpin.adapter=ad

        addBtn.setOnClickListener{
            val d=daySpin.selectedItem.toString()
            val v=valueBox.text.toString()
            if(v.isNotEmpty()){
                data[d]=v.toFloat()
                drawChart()
                valueBox.text.clear()
            }else{
                Toast.makeText(this,"enter value",Toast.LENGTH_SHORT).show()
            }
        }

        toggle.addOnButtonCheckedListener{_,id,check->
            if(check){
                barScroll.visibility=if(id==R.id.btnBar)View.VISIBLE else View.GONE
                lineBox.visibility=if(id==R.id.btnLine)View.VISIBLE else View.GONE
                pieBox.visibility=if(id==R.id.btnPie)View.VISIBLE else View.GONE
                drawChart()
            }
        }

        drawChart()
    }

    fun drawChart(){
        when(toggle.checkedButtonId){
            R.id.btnBar->drawBar()
            R.id.btnLine->drawLine()
            R.id.btnPie->drawPie()
        }
    }

    fun drawBar(){
        barBox.removeAllViews()
        val max=data.values.maxOrNull()?.coerceAtLeast(1f)?:1f
        val maxH=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250f,resources.displayMetrics)
        val scale=maxH/max

        for((i,d)in week.withIndex()){
            val v=data[d]?:0f

            val col=LinearLayout(this)
            col.orientation=LinearLayout.VERTICAL
            col.gravity=Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            col.layoutParams=LinearLayout.LayoutParams(105,LinearLayout.LayoutParams.MATCH_PARENT).apply{
                setMargins(10,0,10,0)
            }

            val bar=View(this)
            bar.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(v*scale).toInt().coerceAtLeast(5))
            bar.setBackgroundColor(colors[i])

            val txt=TextView(this)
            txt.text=d
            txt.gravity=Gravity.CENTER

            col.addView(bar)
            col.addView(txt)

            barBox.addView(col)
        }
    }

    fun drawLine(){
        lineBox.removeAllViews()
        lineBox.addView(LineView(this,data,week))
    }

    fun drawPie(){
        pieBox.removeAllViews()
        pieBox.addView(PieView(this,data,week,colors))
    }
}

class LineView(context:Context,val data:Map<String,Float>,val days:Array<String>):View(context){
    val p=Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color=Color.BLACK
        strokeWidth=5f
        style=Paint.Style.STROKE
    }

    override fun onDraw(c:Canvas){
        val max=data.values.maxOrNull()?.coerceAtLeast(1f)?:1f
        val pad=80f
        val w=width-2*pad
        val h=height-2*pad
        val step=w/(days.size-1)
        val path=Path()

        for(i in days.indices){
            val x=pad+i*step
            val y=height-pad-(data[days[i]]!!/max)*h
            if(i==0)path.moveTo(x,y) else path.lineTo(x,y)
            c.drawCircle(x,y,8f,p)
        }

        c.drawPath(path,p)
    }
}

class PieView(context:Context,val data:Map<String,Float>,val days:Array<String>,val colors:IntArray):View(context){
    val p=Paint(Paint.ANTI_ALIAS_FLAG)
    val r=RectF()

    override fun onDraw(c:Canvas){
        val total=data.values.sum().coerceAtLeast(1f)
        val pad=60f
        r.set(pad,pad,width-pad,height-pad)

        var start=0f
        for(i in days.indices){
            val sweep=(data[days[i]]!!/total)*360f
            p.color=colors[i]
            c.drawArc(r,start,sweep,true,p)
            start+=sweep
        }
    }
}