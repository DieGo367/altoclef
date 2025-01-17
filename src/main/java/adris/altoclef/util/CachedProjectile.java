package adris.altoclef.util;

import adris.altoclef.util.csharpisbetter.Timer;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Type;

public class CachedProjectile {
    public Vec3d velocity;
    public Vec3d position;
    public double gravity;
    public Type projectileType;

    private Timer _lastCache = new Timer(2);
    private Vec3d _cachedHit;
    private boolean _cacheHeld = false;

    public Vec3d getCachedHit() {
        return _cachedHit;
    }
    public void setCacheHit(Vec3d cache) {
        _cachedHit = cache;
        _cacheHeld = true;
        _lastCache.reset();
    }
    public boolean needsToRecache() {
        return !_cacheHeld || _lastCache.elapsed();
    }
}
