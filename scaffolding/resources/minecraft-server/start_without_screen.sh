#!/bin/sh

# Disable Ctrl+C interrupt (to prevent accidents)
stty intr ''
# Run the server
java \
  --add-opens=java.base/java.util=ALL-UNNAMED \
  --add-opens=java.base/java.lang=ALL-UNNAMED \
  --add-opens=java.base/java.net=ALL-UNNAMED \
  --add-opens=java.base/java.security=ALL-UNNAMED \
  --add-opens=java.base/jdk.internal.loader=ALL-UNNAMED \
  --add-modules=jdk.incubator.vector \
  -Xms10G -Xmx10G \
  -XX:+UseG1GC \
  -XX:+ParallelRefProcEnabled \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UnlockExperimentalVMOptions \
  -XX:+UnlockDiagnosticVMOptions \
  -XX:+DisableExplicitGC \
  -XX:+AlwaysPreTouch \
  -XX:G1HeapWastePercent=5 \
  -XX:G1MixedGCCountTarget=4 \
  -XX:G1MixedGCLiveThresholdPercent=90 \
  -XX:G1RSetUpdatingPauseTimePercent=5 \
  -XX:SurvivorRatio=32 \
  -XX:+PerfDisableSharedMem \
  -XX:MaxTenuringThreshold=1 \
  -XX:+UseStringDeduplication \
  -XX:+UseFastUnorderedTimeStamps \
  -XX:+UseAES \
  -XX:+UseAESIntrinsics \
  -XX:+UseFMA \
  -XX:AllocatePrefetchStyle=1 \
  -XX:+UseLoopPredicate \
  -XX:+RangeCheckElimination \
  -XX:+EliminateLocks \
  -XX:+DoEscapeAnalysis \
  -XX:+UseCodeCacheFlushing \
  -XX:+SegmentedCodeCache \
  -XX:+UseFastJNIAccessors \
  -XX:+OptimizeStringConcat \
  -XX:+UseCompressedOops \
  -XX:+UseThreadPriorities \
  -XX:+OmitStackTraceInFastThrow \
  -XX:+TrustFinalNonStaticFields \
  -XX:ThreadPriorityPolicy=1 \
  -XX:+UseInlineCaches \
  -XX:+RewriteBytecodes \
  -XX:+RewriteFrequentPairs \
  -XX:+UseNUMA \
  -XX:+UseFPUForSpilling \
  -XX:+UseVectorCmov \
  -XX:+UseXMMForArrayCopy \
  -XX:G1NewSizePercent=30 \
  -XX:G1MaxNewSizePercent=40 \
  -XX:G1HeapRegionSize=8M \
  -XX:G1ReservePercent=20 \
  -XX:InitiatingHeapOccupancyPercent=15 \
  -Dusing.aikars.flags=https://mcflags.emc.gs \
  -Daikars.new.flags=true \
  -XX:AutoBoxCacheMax=32767 \
  -Dpaper.explicit-flush=true \
  -DPaper.WorkerThreadCount=2 \
  -Ddisabled.suki.threads.levelexecutor=1 \
  -Dsuki.systemcpus.forchunktasks=4 \
  -Dsuki.systemcpus.forexecutors=4 \
  -Dsuki.systemcpus.fortracker=4 \
  -Dsuki.threads.asyncexecutor=4 \
  -Dsuki.threads.tracker=4 \
  -Dsuki.threads.upgradeworld=2 \
  -Dsuki.mainthreadpriority=8 \
  -Dsuki.viewdistance.max=32 \
  -Dpaper.playerconnection.keepalive=300000 \
  -Xlog:async \
  -Dlog4j2.formatMsgNoLookups=true \
  -Djdk.serialFilter='!java.rmi.MarshalledObject' \
  -jar server.jar \
  nogui
# Allow the user to read the logs in the terminal before terminating
read -p "Press enter to continue..."
