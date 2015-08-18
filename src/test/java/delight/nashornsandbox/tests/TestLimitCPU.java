package delight.nashornsandbox.tests;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import delight.nashornsandbox.exceptions.ScriptCPUAbuseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Test;

@SuppressWarnings("all")
public class TestLimitCPU {
  @Test(expected = ScriptCPUAbuseException.class)
  public void test() {
    final NashornSandbox sandbox = NashornSandboxes.create();
    try {
      sandbox.setMaxCPUTime(50);
      ExecutorService _newSingleThreadExecutor = Executors.newSingleThreadExecutor();
      sandbox.setExecutor(_newSingleThreadExecutor);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("var x = 1;");
      _builder.newLine();
      _builder.append("while (true) {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("x=x+1;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      sandbox.eval(_builder.toString());
    } finally {
      ExecutorService _executor = sandbox.getExecutor();
      _executor.shutdown();
    }
  }
  
  @Test(expected = ScriptCPUAbuseException.class)
  public void test_evil_script() {
    final NashornSandbox sandbox = NashornSandboxes.create();
    try {
      sandbox.setMaxCPUTime(50);
      ExecutorService _newSingleThreadExecutor = Executors.newSingleThreadExecutor();
      sandbox.setExecutor(_newSingleThreadExecutor);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("var x = 1;");
      _builder.newLine();
      _builder.append("while (true) { }");
      _builder.newLine();
      sandbox.eval(_builder.toString());
    } finally {
      ExecutorService _executor = sandbox.getExecutor();
      _executor.shutdown();
    }
  }
  
  @Test
  public void test_nice_script() {
    final NashornSandbox sandbox = NashornSandboxes.create();
    sandbox.setMaxCPUTime(50);
    ExecutorService _newSingleThreadExecutor = Executors.newSingleThreadExecutor();
    sandbox.setExecutor(_newSingleThreadExecutor);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("var x = 1;");
    _builder.newLine();
    _builder.append("for (var i=0;i<=1000;i++) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("x = x + i");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    sandbox.eval(_builder.toString());
    ExecutorService _executor = sandbox.getExecutor();
    _executor.shutdown();
  }
  
  @Test(expected = ScriptCPUAbuseException.class)
  public void test_catch() {
    final NashornSandbox sandbox = NashornSandboxes.create();
    try {
      sandbox.setMaxCPUTime(50);
      ExecutorService _newSingleThreadExecutor = Executors.newSingleThreadExecutor();
      sandbox.setExecutor(_newSingleThreadExecutor);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("try {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("var x = 1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("while (true) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("x=x+1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("} catch (e) {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// never call me!");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      sandbox.eval(_builder.toString());
    } finally {
      ExecutorService _executor = sandbox.getExecutor();
      _executor.shutdown();
    }
  }
}
