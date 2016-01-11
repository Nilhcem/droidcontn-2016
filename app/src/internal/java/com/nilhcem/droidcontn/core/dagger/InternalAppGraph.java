package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.InternalDroidconApp;

public interface InternalAppGraph extends AppGraph {

    void inject(InternalDroidconApp app);
}
