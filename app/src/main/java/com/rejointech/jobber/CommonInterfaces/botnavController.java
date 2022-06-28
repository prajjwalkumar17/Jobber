package com.rejointech.jobber.CommonInterfaces;

public interface botnavController {
    interface botVisibilityController {

        void setDrawerLocked();

        void setDrawerunLocked();

        void setToolbarInvisible();

        void setToolbarVisible();

        public void setbotInvisible();

        public void setbotVisible();

        public void setfabinvisible();

        public void setfabvisible();
    }


    interface drawerControl {
        public void setDrawerLocked();

        public void setDrawerunLocked();
    }

    interface toolbarController {
        public void setToolbarInvisible();

        public void setToolbarVisible();
    }
}
