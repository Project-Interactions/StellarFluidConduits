package com.tfar.stellarfluidconduits.common.conduit;

import com.enderio.core.api.client.gui.IAdvancedTooltipProvider;
import com.tfar.stellarfluidconduits.common.conduit.stellar.StellarFluidConduit;
import com.tfar.stellarfluidconduits.common.conduit.stellar.StellarFluidConduitRenderer;
import com.tfar.stellarfluidconduits.common.config.StellarFluidConduitConfig;
import crazypants.enderio.api.IModObject;
import crazypants.enderio.base.EnderIO;
import crazypants.enderio.base.conduit.ConduitDisplayMode;
import crazypants.enderio.base.conduit.IConduit;
import crazypants.enderio.base.conduit.IServerConduit;
import crazypants.enderio.base.conduit.registry.ConduitDefinition;
import crazypants.enderio.base.conduit.registry.ConduitRegistry;
import crazypants.enderio.base.gui.IconEIO;
import crazypants.enderio.conduits.conduit.AbstractItemConduit;
import crazypants.enderio.conduits.conduit.ItemConduitSubtype;
import crazypants.enderio.conduits.conduit.liquid.ILiquidConduit;
import crazypants.enderio.conduits.render.ConduitBundleRenderManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemStellarFluidConduit extends AbstractItemConduit implements IAdvancedTooltipProvider {


  public static ItemStellarFluidConduit create(@Nonnull IModObject modObject, @Nullable Block block) {
    return new ItemStellarFluidConduit(modObject);
  }

  public ItemStellarFluidConduit(@Nonnull IModObject modObject) {
    super(modObject, new ItemConduitSubtype(modObject.getUnlocalisedName(), modObject.getRegistryName().toString()));

    ConduitRegistry.injectMember(new ConduitDefinition(ConduitRegistry.get(UUID.nameUUIDFromBytes(
            (new ResourceLocation(EnderIO.DOMAIN, "fluid"))
                    .toString().getBytes())).getNetwork(), UUID.nameUUIDFromBytes("Random UUID".getBytes()),
            StellarFluidConduit.class, StellarFluidConduit.class));


    ConduitDisplayMode.registerDisplayMode(new ConduitDisplayMode(getBaseConduitType(), IconEIO.WRENCH_OVERLAY_FLUID, IconEIO.WRENCH_OVERLAY_FLUID_OFF));


  }


  @Override
  @SideOnly(Side.CLIENT)
  public void registerRenderers(@Nonnull IModObject modObject) {
    super.registerRenderers(modObject);
    ConduitBundleRenderManager.instance.getConduitBundleRenderer().registerRenderer(new StellarFluidConduitRenderer());
  }

  @Nonnull
  @Override
  public Class<? extends IConduit> getBaseConduitType() {
    return ILiquidConduit.class;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addCommonEntries(@Nonnull ItemStack itemStack, @Nullable EntityPlayer entityPlayer, @Nonnull List<String> list, boolean b) {

  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addBasicEntries(@Nonnull ItemStack itemStack, @Nullable EntityPlayer entityPlayer, @Nonnull List<String> list, boolean b) {

  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addDetailedEntries(@Nonnull ItemStack itemStack, @Nullable EntityPlayer entityPlayer, @Nonnull List<String> list, boolean b) {
    int extractRate;
    int maxIo;
    extractRate = StellarFluidConduitConfig.extractRate.get();
    maxIo = StellarFluidConduitConfig.maxIO.get();


    String mbt = new TextComponentTranslation("stellarfluidconduits.fluid.millibuckets_tick").getUnformattedComponentText();
    list.add(new TextComponentTranslation("stellarfluidconduits.item_fluid_conduit.tooltip.max_extract").getUnformattedComponentText() + " " + extractRate + mbt);
    list.add(new TextComponentTranslation("stellarfluidconduits.item_fluid_conduit.tooltip.max_io").getUnformattedComponentText() + " " + maxIo + mbt);

  }

  @Override
  public IServerConduit createConduit(@Nonnull ItemStack item, @Nonnull EntityPlayer player) {
    return new StellarFluidConduit();
  }

  @Override
  public boolean shouldHideFacades(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
    return true;
  }
}