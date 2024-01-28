package at.gkgo.wurm;

import at.gkgo.canon.block.ModBlockWithEntity;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class WurmholeBlock extends ModBlockWithEntity<WurmholeBlock,WurmholeBlockEntity,WurmholeItem> {
    public WurmholeBlock(Supplier<BlockEntityType<WurmholeBlockEntity>> type) {
        super(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK), type);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if(player.getStackInHand(hand).isOf(Items.ECHO_SHARD)){
                if(!world.isClient){
                    Wurmhole.LOGGER.info("in hit");
                var o = ((WurmholeBlockEntity)world.getBlockEntity(pos)).getTarget();
                FabricDimensions.teleport(player,(ServerWorld) o.getWorld(),new TeleportTarget(o.getPos().up().toCenterPos(), Vec3d.ZERO,player.getYaw(),player.getPitch()));
            }
                return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
