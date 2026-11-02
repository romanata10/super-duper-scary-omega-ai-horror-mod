package com.omega.horror.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Встроенный «рабочий» браузер внутри блока-ПК.
 */
public class BrowserScreen extends Screen {
    private static final Random RANDOM = new Random();

    private static final Map<String, List<String>> PAGES = new HashMap<>();

    private static final String[] SEARCH_RESULTS = {
            "1. как вступить в null — 404, null не найден",
            "2. david и коровы: полная история",
            "3. торнадо своими руками из воронки",
            "4. 240 кнопок БЕСПЛАТНО (нажмите сюда)",
            "5. история браузера насоса василия",
            "6. молоко: лить или пить? débat",
            "7. krita: топ-8 злых рисунков",
            "8. nullscape mart — обзор магазина"
    };

    static {
        PAGES.put("home", List.of(
                "🌐 Добро пожаловать в OmegaBrowser 3000!",
                "",
                "Введите адрес в строке сверху, например:",
                "  • home",
                "  • horror",
                "  • nullscape",
                "  • iris",
                "  • search:<любой запрос>",
                "",
                "Внимание: история этого браузера шуточная."
        ));
        PAGES.put("horror", List.of(
                "😱 HORROR INDEX 😱",
                "",
                "• David крадёт коров и молоко...",
                "• Null смотрит из темноты...",
                "• Насос Василий где-то в 250 метрах...",
                "• Злые рыбки кусаются...",
                "• Кнопочный монстр расставляет кнопки..."
        ));
        PAGES.put("nullscape", List.of(
                "🛒 NULLSCAPE MART 🛒",
                "",
                "Сегодня в продаже:",
                "  - 1 (один) стакан молока — SOLD OUT (david забрал)",
                "  - 240 кнопок — по цене одной души",
                "  - тьма — бесплатно, но она заберёт вас",
                "  - носки фембойчика — +1000 HP, +100 силы"
        ));
        PAGES.put("iris", List.of(
                "🪐 IRIS — планета из analog horror",
                "",
                "gemini home entertainment представляет:",
                "планета IRIS. там тихо. слишком тихо.",
                "координаты: /execute in omega_horror:iris",
                "не смотрите на неё долго."
        ));
    }

    private final BlockPos pos;
    private EditBox addressBar;
    private final List<String> history = new ArrayList<>();
    private int historyIndex = -1;

    public BrowserScreen(BlockPos pos) {
        super(Component.literal("OmegaBrowser 3000"));
        this.pos = pos;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        this.addressBar = new EditBox(this.font, centerX - 140, 20, 220, 20, Component.literal("Адрес"));
        this.addressBar.setValue("home");
        this.addRenderableWidget(this.addressBar);
        this.addRenderableWidget(Button.builder(Component.literal("<-"), b -> this.back())
                .bounds(centerX - 180, 20, 30, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Go"), b -> this.go())
                .bounds(centerX + 88, 20, 40, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("->"), b -> this.forward())
                .bounds(centerX + 132, 20, 30, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("X"), b -> this.onClose())
                .bounds(this.width - 30, 20, 25, 20).build());
    }

    private void go() {
        String url = this.addressBar.getValue().trim();
        if (url.isEmpty()) {
            url = "home";
        }
        if (this.historyIndex < this.history.size() - 1) {
            this.history.subList(this.historyIndex + 1, this.history.size()).clear();
        }
        this.history.add(url);
        this.historyIndex = this.history.size() - 1;
    }

    private void back() {
        if (this.historyIndex > 0) {
            this.historyIndex--;
            this.addressBar.setValue(this.history.get(this.historyIndex));
        }
    }

    private void forward() {
        if (this.historyIndex < this.history.size() - 1) {
            this.historyIndex++;
            this.addressBar.setValue(this.history.get(this.historyIndex));
        }
    }

    private List<String> pageFor(String url) {
        if (url.startsWith("search:")) {
            String query = url.substring("search:".length());
            List<String> lines = new ArrayList<>();
            lines.add("🔎 Результаты поиска по запросу: \"" + query + "\"");
            lines.add("");
            int count = 3 + RANDOM.nextInt(4);
            for (int i = 0; i < count; i++) {
                lines.add(SEARCH_RESULTS[RANDOM.nextInt(SEARCH_RESULTS.length)]);
            }
            return lines;
        }
        List<String> page = PAGES.get(url);
        if (page != null) {
            return page;
        }
        return List.of(
                "❌ 404: страница \"" + url + "\" не найдена.",
                "Возможно, её украл David. Или null."
        );
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        int centerX = this.width / 2;
        graphics.fill(centerX - 200, 50, centerX + 200, this.height - 20, 0xFF181818);
        graphics.drawCenteredString(this.font, "🌐 OmegaBrowser 3000 — встроенный браузер ПК",
                centerX, 56, 0x55FF55);
        graphics.drawCenteredString(this.font, "блок на координатах: " + this.pos.getX() + " " + this.pos.getY() + " " + this.pos.getZ(),
                centerX, this.height - 14, 0x666666);

        List<String> lines = this.pageFor(this.addressBar.getValue().trim().isEmpty()
                ? "home" : this.addressBar.getValue().trim());
        int y = 78;
        for (String line : lines) {
            if (y > this.height - 30) {
                break;
            }
            graphics.drawString(this.font, line, centerX - 190, y, 0xCCCCCC);
            y += 12;
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
