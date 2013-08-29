package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class PhotoUploadForm extends Composite {

	/**** Ui-Binder stuff ****/
	private static PhotoUploadFormUiBinder uiBinder = GWT
			.create(PhotoUploadFormUiBinder.class);

	interface PhotoUploadFormUiBinder extends UiBinder<Widget, PhotoUploadForm> {
	}

	public interface PhotoUploadListener {
		public void photoUploaded(Photo photo);
	}

	/**** other fields ****/
	private PhotoUploadListener listener;

	/**** Ui-Fields ****/
	@UiField
	FormPanel form;
	@UiField
	Button uploadButton;
	@UiField
	FileUpload fileupload;

	public PhotoUploadForm(PhotoUploadListener listener) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.listener = listener;

		form.setAction("/Upload/uploader");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		fileupload.setName("img");
		// Add an event handler to the form.

		form.addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				System.out.println("Submitted form.");
			}
		});

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				System.out.println("Submit complete.");

				final String url = event.getResults().replace("<pre>", "")
						.replace("</pre>", "").trim();

				if (url.startsWith("http://")) {
					Requests.getNewEntity(Photo.type,
							new RequestListener<Photo>() {
								@Override
								public void onNewResult(final Photo photo) {
									photo.setUser("anonym");
									photo.setTimestamp(Timestamp.now());
									photo.setImage(new ImageUrl(url));
									PhotoUploadForm.this.listener
											.photoUploaded(photo);
								}
							});
				} else
					Window.alert("Beim Upload ist ein Fehler aufgetreten. Bitt versuchen Sie es erneut.");
				Log.log("Photo upload error. Instead of an url, we got this: "
						+ url);
			}
		});
	}

	@UiHandler("uploadButton")
	void onUpload(ClickEvent e) {
		if (fileupload.getFilename() != null
				&& fileupload.getFilename().length() > 0) {
			System.out.println("Image File Name: " + fileupload.getFilename());
			form.submit();
		}
	}
}
