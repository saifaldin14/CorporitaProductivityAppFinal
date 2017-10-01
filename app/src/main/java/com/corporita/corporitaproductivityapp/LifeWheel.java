package com.corporita.corporitaproductivityapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by saif on 2017-08-29.
 */

public class LifeWheel extends View {

    private static final String TAG = "Testing";



    public  LifeWheel(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = View.MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(w,w);


    }

    @Override
    protected void onDraw(Canvas canvas) {


        //Base Properties
        float w = canvas.getWidth();
        float h = canvas.getWidth();
        float maxSize = (float)(w * 0.8);
        float center = w/2;
        float maxRadius = maxSize/2;


        String [] getCategories = GoalsPage.goalsCat;
        int[] getProgress = GoalsPage.goalsProgress;

        //Testing paint

        Paint testPaint = new Paint();
        testPaint.setColor(0xff000000);
        testPaint.setStyle(Paint.Style.FILL);


        ///PAINT///

        //Health
        Paint healthPaint = new Paint();
        healthPaint.setColor(0xffff001a);
        healthPaint.setStyle(Paint.Style.FILL);

        //Career
        Paint careerPaint = new Paint();
        careerPaint.setColor(0xff113a0c);
        careerPaint.setStyle(Paint.Style.FILL);

        //Finance
        Paint financePaint = new Paint();
        financePaint.setColor(0xffffa500);
        financePaint.setStyle(Paint.Style.FILL);

        //Family
        Paint familyPaint = new Paint();
        familyPaint.setColor(0xff6500ff);
        familyPaint.setStyle(Paint.Style.FILL);


        //Recreation
        Paint recreationPaint = new Paint();
        recreationPaint.setColor(0xff9aff00);
        recreationPaint.setStyle(Paint.Style.FILL);

        //Personal
        Paint personalPaint = new Paint();
        personalPaint.setColor(0xff005aff);
        personalPaint.setStyle(Paint.Style.FILL);

        //RelationShip
        Paint relationshipPaint = new Paint();
        relationshipPaint.setColor(0xffff00d9);
        relationshipPaint.setStyle(Paint.Style.FILL);

        //Social
        Paint socialPaint = new Paint();
        socialPaint.setColor(0xff21796f);
        socialPaint.setStyle(Paint.Style.FILL);




        float[] categoryRatios = getDetailsFromDatabase(getCategories,getProgress);
        Paint[] categoryPaints = new Paint[]{healthPaint,careerPaint,financePaint,familyPaint,recreationPaint,personalPaint,relationshipPaint,socialPaint};

        for (int i=0; i < 8; i++){
            int startAngle = i * 45;
            int sweepAngle = 45;
            boolean useCenter = true;
            float radius = maxRadius * categoryRatios[i];
            RectF slice = new RectF(center - radius, center - radius, center + radius, center + radius);
            Paint paintUsed = categoryPaints[i];

            canvas.drawArc(slice, startAngle, sweepAngle, useCenter, paintUsed);
        }







        //canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, canvas.getWidth()/2, outlinePaint);
    }

    //Health, Career, Finance, Family, Personal, Recreation, Relationship, Social

    public float[] getDetailsFromDatabase(String[] cat, int[] pro){
        float healthNu = 0;
        float healthDe = 0;

        float careerNu = 0;
        float careerDe = 0;

        float financeNu = 0;
        float financeDe = 0;

        float personalNu = 0;
        float personalDe = 0;

        float recreationNu = 0;
        float recreationDe = 0;

        float familyNu = 0;
        float familyDe = 0;

        float relationshipNu = 0;
        float relationshipDe = 0;

        float socialNu = 0;
        float socialDe = 0;

        int length = cat.length;

        for (int i=0; i< length; i++ ){
            String category = cat[i];
            int progress = pro[i];

            switch (category){
                case "Health":
                    healthNu += progress;
                    healthDe += 100;
                    break;

                case "Career":
                    careerNu += progress;
                    careerDe += 100;
                    break;

                case "Finance":
                    financeNu += progress;
                    financeDe += 100;
                    break;

                case "Family":
                    familyNu += progress;
                    familyDe += 100;
                    break;

                case "Personal":
                    personalNu += progress;
                    personalDe += 100;
                    break;

                case "Recreation":
                    recreationNu += progress;
                    recreationDe += 100;
                    break;

                case "Relationship":
                    relationshipNu += progress;
                    relationshipDe += 100;
                    break;

                case "Social":
                    socialNu += progress;
                    socialDe += 100;
                    break;
                default:
                    break;

            }
        }

        float healthR = getRatio(healthNu, healthDe);
        float careerR = getRatio(careerNu, careerDe);
        float financeR = getRatio(financeNu, financeDe);
        float familyR = getRatio(familyNu, familyDe);
        float personalR = getRatio(personalNu, personalDe);
        float recreationR = getRatio(recreationNu, recreationDe);
        float relationshipR = getRatio(relationshipNu, relationshipDe);
        float socialR = getRatio(socialNu, socialDe);

        float [] ratios = new float[]{healthR, careerR, financeR,familyR,recreationR,personalR,relationshipR,socialR};

        return ratios;



    }

    //Health, Career, Finance, Family, Personal, Recreation, Relationship, Social

    public float getRatio(float nu,float de){
        float r = nu/de;
        if (de == 0){
            r = (float) 0;
        }
        return r;
    }
}


