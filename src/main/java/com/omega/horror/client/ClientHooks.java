package com.omega.horror.client;

import com.omega.horror.client.screen.BrowserScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openBrowser(BlockPos pos) {
        Minecraft.getInstance().setScreen(new BrowserScreen(pos));
    }
}
