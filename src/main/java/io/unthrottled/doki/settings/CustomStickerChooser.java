package io.unthrottled.doki.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import io.unthrottled.doki.promotions.MessageBundle;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CustomStickerChooser extends DialogWrapper {
  private JPanel contentPane;
  private TextFieldWithBrowseButton textField1;

  public CustomStickerChooser(Project project, String path) {
    super(project, true);
    textField1.addBrowseFolderListener(project, FileChooserDescriptorFactory.singleFile());
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
    textField1 = new TextFieldWithBrowseButton();
  }

  public String getPath() {
    return textField1.getText();
  }
}
