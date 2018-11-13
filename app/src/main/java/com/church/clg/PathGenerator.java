package com.church.clg;

import android.util.Log;

public  class PathGenerator {

    //start photo number
        int photoNumber = 0;
        //boolean that indicates if the path has been used already or not.
        boolean pathIsTaken = false;
        //our storage path.
        String path;

        /**
         * This method will generate a new path.
         * while path is taken , wait because we didn't use it yet.
         *
         * @param photoNumber
         */

        public synchronized String generatePath(int photoNumber) {

            while (pathIsTaken)
            {
                try {wait();} catch (Exception e) {

                }
            }
            this.photoNumber = photoNumber;
            path = "album/" + String.valueOf(photoNumber) + ".jpg";
            pathIsTaken = true;
            Log.e("eyaldebug", "path is :  " + path);
            return path;
        }

        /**
         * Simple set method.
         * @param value
         */

        public synchronized void setPathisSet(boolean value)
        {
            this.pathIsTaken = value;
        }

        /**
         * Unfreeze the thread, we call this method after onSucsess.
         */

        public synchronized void unfreeze( )
        {
            notifyAll();
        }

}







