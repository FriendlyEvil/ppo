package ru.friendlyevil.profiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.friendlyevil.application.Application;
import ru.friendlyevil.profiler.configuration.ContextConfiguration;
import ru.friendlyevil.profiler.service.ProfilerService;
import ru.friendlyevil.profiler.service.VisualizeStatisticService;

public class Main {
    private final ProfilerService profilerService;
    private final VisualizeStatisticService visualizeStatisticService;

    @Autowired
    public Main(ProfilerService profilerService, VisualizeStatisticService visualizeStatisticService) {
        this.profilerService = profilerService;
        this.visualizeStatisticService = visualizeStatisticService;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        new Main(ctx.getBean(ProfilerService.class), ctx.getBean(VisualizeStatisticService.class)).run();
    }

    public void run() {
        new Application().run();
        visualizeStatisticService.visualizeTree(profilerService.getStatistic());
    }
}
