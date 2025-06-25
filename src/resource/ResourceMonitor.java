package resource;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResourceMonitor {
    private static ResourceMonitor instance;

    private final SystemInfo systemInfo;
    private final CentralProcessor processor;
    private final GlobalMemory memory;
    private final FileSystem fileSystem;

    private long[] prevTicks;
    private long[][] prevProcTicks;

    private String cpuUsage;
    private String perCoreCpuUsage;
    private String memoryUsage;
    private String diskUsage;

    private final ScheduledExecutorService scheduler;

    private ResourceMonitor() {
        systemInfo = new SystemInfo();
        processor = systemInfo.getHardware().getProcessor();
        memory = systemInfo.getHardware().getMemory();
        fileSystem = systemInfo.getOperatingSystem().getFileSystem();

        prevTicks = processor.getSystemCpuLoadTicks();
        prevProcTicks = processor.getProcessorCpuLoadTicks();

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateResources, 0, 500, TimeUnit.MILLISECONDS);
    }

    public static synchronized ResourceMonitor getInstance() {
        if (instance == null) {
            instance = new ResourceMonitor();
        }
        return instance;
    }

    private void updateResources() {
        updateCpuUsage();
        updatePerCoreCpuUsage();
        updateMemoryUsage();
        updateDiskUsage();
    }

    private void updateCpuUsage() {
        long[] ticksNow = processor.getSystemCpuLoadTicks();
        double load = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = ticksNow;
        cpuUsage = String.format("CPU: %.1f%%", load);
    }

    private void updatePerCoreCpuUsage() {
        long[][] currentTicks = processor.getProcessorCpuLoadTicks();
        double[] loadPerCore = processor.getProcessorCpuLoadBetweenTicks(prevProcTicks);
        prevProcTicks = currentTicks;

        StringBuilder result = new StringBuilder("Uso da CPU por núcleo:\n");
        for (int i = 0; i < loadPerCore.length; i++) {
            result.append(String.format("Núcleo %d: %.1f%%\n", i, loadPerCore[i] * 100));
        }
        perCoreCpuUsage = result.toString();
    }

    private void updateMemoryUsage() {
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long used = total - available;
        memoryUsage = String.format("Memória RAM: %.2f GB usados / %.2f GB total (%.2f GB Livre)",
                used / 1e9, total / 1e9, available / 1e9);
    }

    private void updateDiskUsage() {
        StringBuilder result = new StringBuilder();
        for (OSFileStore fs : fileSystem.getFileStores()) {
            long totalSpace = fs.getTotalSpace();
            long usableSpace = fs.getUsableSpace();
            long usedSpace = totalSpace - usableSpace;
            result.append(String.format("%s: %.2f GB usados / %.2f GB total (%.2f GB Livre)\n",
                    fs.getMount(), usedSpace / 1e9, totalSpace / 1e9, usableSpace / 1e9));
        }
        diskUsage = result.toString();
    }

    // Accessor methods now return the last updated values
    public String getCPUUsage() {
        return cpuUsage != null ? cpuUsage : "Carregando CPU...";
    }

    public String getPerCoreCPUUsage() {
        return perCoreCpuUsage != null ? perCoreCpuUsage : "Carregando núcleos...";
    }

    public String getMemoryUsage() {
        return memoryUsage != null ? memoryUsage : "Carregando memória...";
    }

    public String getDiskUsage() {
        return diskUsage != null ? diskUsage : "Carregando disco...";
    }
}