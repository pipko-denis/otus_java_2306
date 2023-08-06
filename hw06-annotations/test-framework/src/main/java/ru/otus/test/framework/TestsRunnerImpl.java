package ru.otus.test.framework;

import lombok.RequiredArgsConstructor;
import ru.otus.test.framework.services.api.ClassResearchService;
import ru.otus.test.framework.services.api.InformingService;
import ru.otus.test.framework.services.impl.StandardOutputInformingService;
import ru.otus.test.framework.services.api.ClassTestResults;
import ru.otus.test.framework.services.api.ClassRunService;
import ru.otus.test.framework.services.impl.DefaultClassRunService;
import ru.otus.test.framework.services.impl.DefaultClassResearchService;
import ru.otus.test.framework.services.api.TestableClass;

import java.util.List;

@RequiredArgsConstructor
public class TestsRunnerImpl implements TestsRunner {


    private final ClassResearchService researchService;
    private final ClassRunService classRunService;
    private final InformingService informingService;

    /**
     * Instance created by this constructor will use DefaultClassResearcher, DefaultClassTestRunService and StandardOutputInformingService
     *
     * @see DefaultClassResearchService
     * @see DefaultClassRunService
     * @see StandardOutputInformingService
     */
    public TestsRunnerImpl() {
        this.researchService = new DefaultClassResearchService();
        this.classRunService = new DefaultClassRunService();
        this.informingService = new StandardOutputInformingService();
    }

    @Override
    public void runTests(List<Class<?>> classesList) {

        List<TestableClass> classTestsList = researchService.getTests(classesList);

        for (TestableClass testableClass : classTestsList) {
            ClassTestResults runResults = classRunService.runClassTestMethods(testableClass);
            informingService.informUser(runResults);
        }
    }

}
