package net.d1g1byt3.derpapropos.events;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.entity.ModEntities;
import net.d1g1byt3.derpapropos.entity.client.RhinoModel;
import net.d1g1byt3.derpapropos.entity.custom.RhinoEntity;
import net.d1g1byt3.derpapropos.entity.layers.ModModelLayers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DerpAproposMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.RHINO.get(), RhinoEntity.createAttributes().build());
    }
}
