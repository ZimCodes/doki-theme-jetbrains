package io.unthrottled.doki.settings;

import com.intellij.ide.util.BrowseFilesListener;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.BrowseFolderRunnable;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.ui.components.fields.ExtendableTextField;
import io.unthrottled.doki.promotions.MessageBundle;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomStickerChooser extends DialogWrapper {
  private JPanel contentPane;
  private JTextField textField1;
  private final Project project;

  public CustomStickerChooser(Project project, String path) {
    super(project, true);
    this.project = project;

    setTitle(MessageBundle.message("settings.general.content.custom.sticker.modal.title"));
    textField1.setText(path);

    init();
    pack();
  }


  @Override
  protected @Nullable JComponent createCenterPanel() {
    return contentPane;
  }

  private void createUIComponents() {
    ExtendableTextField extendableTextField = new ExtendableTextField();
    textField1 = extendableTextField.addBrowseExtension(
      new BrowseFolderRunnable<>(
        this.project,
        FileChooserDescriptorFactory.singleFile(),
        extendableTextField,
        TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT
      ), null
    );
  }

  public String getPath() {
    return textField1.getText();
  }
}
