package adris.altoclef.util.progresscheck;

import adris.altoclef.AltoClef;
import adris.altoclef.util.WorldUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MovementProgressChecker {

    private final IProgressChecker<Vec3d> _distanceChecker;
    private final IProgressChecker<Double> _mineChecker;

    private BlockPos _lastBreakingBlock = null;

    public MovementProgressChecker(double distanceTimeout, double minDistance, double mineTimeout, double minMineProgress, int attempts) {
        _distanceChecker = new ProgressCheckerRetry<>(new DistanceProgressChecker(distanceTimeout, minDistance), attempts);
        _mineChecker = new LinearProgressChecker(mineTimeout, minMineProgress);
    }
    public MovementProgressChecker(double distanceTimeout, double minDistance, double mineTimeout, double minMineProgress) {
        this(distanceTimeout, minDistance, mineTimeout, minMineProgress, 1);
    }

    public MovementProgressChecker(int attempts) {
        this(4, 0.1, 4, 0.001, attempts);
    }
    public MovementProgressChecker() {
        this(1);
    }

    public boolean check(AltoClef mod) {
        // We just broke a block, we made progress.
        if (_lastBreakingBlock != null && WorldUtil.isAir(mod, _lastBreakingBlock)) {
            _distanceChecker.reset();
            _mineChecker.reset();
            _lastBreakingBlock = null;
        }
        if (mod.getControllerExtras().isBreakingBlock()) {
            // If we broke a block, we made progress.
            // We must also delay reseting the distance checker UNTIL we break a block.
            // Because otherwise we risk not failing if we keep retrtying to mine and don't succeed.
            _lastBreakingBlock = mod.getControllerExtras().getBreakingBlockPos();
            _mineChecker.setProgress(mod.getControllerExtras().getBreakingBlockProgress());
            if (_mineChecker.failed()) return false;
        } else {
            _mineChecker.reset();
            _distanceChecker.setProgress(mod.getPlayer().getPos());
            if (_distanceChecker.failed()) return false;
        }
        return true;
    }

    public void reset(AltoClef mod) {
        // Run an update.
        check(mod);
        _distanceChecker.reset();
        _mineChecker.reset();
    }
}
