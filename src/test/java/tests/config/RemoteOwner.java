package tests.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:remote.properties"
})
public interface RemoteOwner extends Config{

    String url();

}