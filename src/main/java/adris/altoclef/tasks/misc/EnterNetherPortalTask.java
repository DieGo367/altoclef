package adris.altoclef.tasks.misc;

import adris.altoclef.AltoClef;
import adris.altoclef.tasks.DoToClosestBlockTask;
import adris.altoclef.tasks.GetToBlockTask;
import adris.altoclef.tasksystem.Task;
import adris.altoclef.util.Dimension;
import adris.altoclef.util.WorldUtil;
import adris.altoclef.util.csharpisbetter.Timer;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class EnterNetherPortalTask extends Task {

    private final Task _getPortalTask;
    private final Dimension _targetDimension;

    private Timer _portalTimeout = new Timer(10);
    private TimeoutWanderTask _wanderTask = new TimeoutWanderTask(3);

    private boolean _leftPortal;

    public EnterNetherPortalTask(Task getPortalTask, Dimension targetDimension) {
        if (targetDimension == Dimension.END) throw new IllegalArgumentException("Can't build a nether portal to the end.");
        _getPortalTask = getPortalTask;
        _targetDimension = targetDimension;
    }

    @Override
    protected void onStart(AltoClef mod) {
        mod.getBlockTracker().trackBlock(Blocks.NETHER_PORTAL);
        _leftPortal = false;
        _portalTimeout.reset();

        _wanderTask.resetWander();
    }

    @Override
    protected Task onTick(AltoClef mod) {

        if (_wanderTask.isActive() && !_wanderTask.isFinished(mod)) {
            setDebugState("Exiting portal for a bit.");
            _portalTimeout.reset();
            _leftPortal = true;
            return _wanderTask;
        }

        if (mod.getWorld().getBlockState(mod.getPlayer().getBlockPos()).getBlock() == Blocks.NETHER_PORTAL) {

            if (_portalTimeout.elapsed() && !_leftPortal) {
                return _wanderTask;
            }
            setDebugState("Waiting inside portal");
            return null;
        } else {
            _portalTimeout.reset();
        }

        BlockPos portal = mod.getBlockTracker().getNearestTracking(mod.getPlayer().getPos(),
                block -> {
                    // REQUIRE that there be solid ground beneath us.
                    BlockPos below = block.down();
                    boolean canStand = WorldUtil.isSolid(mod, below);
                    return !canStand;
                },
                Blocks.NETHER_PORTAL);
        if (portal != null) {
            setDebugState("Going to found portal");
            return new DoToClosestBlockTask(mod, () -> mod.getPlayer().getPos(),  (blockpos) -> new GetToBlockTask(blockpos, false), Blocks.NETHER_PORTAL);
        }
        setDebugState("Getting our portal");
        return _getPortalTask;
    }

    @Override
    protected void onStop(AltoClef mod, Task interruptTask) {
        mod.getBlockTracker().stopTracking(Blocks.NETHER_PORTAL);
    }

    @Override
    public boolean isFinished(AltoClef mod) {
        return mod.getCurrentDimension() == _targetDimension;
    }

    @Override
    protected boolean isEqual(Task obj) {
        if (obj instanceof EnterNetherPortalTask) {
            EnterNetherPortalTask task = (EnterNetherPortalTask) obj;
            return (task._getPortalTask.equals(_getPortalTask) && task._targetDimension.equals(_targetDimension));
        }
        return false;
    }

    @Override
    protected String toDebugString() {
        return "Entering nether portal";
    }
}
