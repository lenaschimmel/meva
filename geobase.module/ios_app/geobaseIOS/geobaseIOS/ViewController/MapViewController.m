//
//  MapViewController.m
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "MapViewController.h"
#import "MapsObject.h"
#import "Marker.h"

@interface MapViewController (){
    MapsObject *myMapsObject;
    CLLocationCoordinate2D *startCoordinate;
    UIImageView *defaultMarkerImageView;
    CGRect upPosition;
    CGRect downPosition;
}

@end

@implementation MapViewController

- (id)init {
    [super doesNotRecognizeSelector:_cmd];
    return nil;
}

- (id)initWithMapsObject:(MapsObject *)mapsObject{
    self = [super init];
    if (self) {
        myMapsObject = mapsObject;
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated{
    //setColors
    self.navigationController.navigationBar.tintColor = myMapsObject.firstColor;
    self.title = myMapsObject.name;
    
    //setRegion
    CLLocationDegrees longitude = myMapsObject.longitude;
    CLLocationDegrees latitude = myMapsObject.latitude;
    CLLocationCoordinate2D centerCoordinate = CLLocationCoordinate2DMake(longitude, latitude);
    MKCoordinateRegion viewRegion = MKCoordinateRegionMakeWithDistance(centerCoordinate, 5000, 5000);
    MKCoordinateRegion adjustedRegion = [self.mapView regionThatFits:viewRegion];

    NSLog(@"Visible Map Rect Höhe: %f",_mapView.visibleMapRect.size.width*0.5);

    
    //setDefaultMarkerPosition
    UIImage *defaultMarker = [UIImage imageNamed:@"caution.png"];
    defaultMarkerImageView = [[UIImageView alloc] initWithImage:defaultMarker];
    [defaultMarkerImageView setFrame:CGRectMake(_mapView.center.x,_mapView.center.y, defaultMarker.size.width, defaultMarker.size.height)];
    //[defaultMarkerImageView setCenter:_mapView.window.center];
    [self.view addSubview:defaultMarkerImageView];
    [self.view bringSubviewToFront:defaultMarkerImageView];

    [self.mapView setRegion:adjustedRegion animated:YES];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    UIImage *defaultMarker = [UIImage imageNamed:@"caution.png"];

    upPosition = CGRectMake(150, 190, defaultMarker.size.width, defaultMarker.size.height);
    downPosition = CGRectMake(150, 210, defaultMarker.size.width, defaultMarker.size.height);
    
    defaultMarkerImageView.frame = upPosition;

    [_mapView setDelegate:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark UIbuttons

- (IBAction)makerButtonPressed:(id)sender {
    
    if (_crosshairs.alpha == 0) {
        _crosshairs.alpha = 1;
        _markerButton.Hidden=YES;
    }
    else _crosshairs.alpha = 0;
    _markerButton.Hidden=NO;

}

/*
 This method creates a new region based on the center coordinate of the map view.
 A new annotation is created to represent the region and then the application starts monitoring the new region.
 */
- (IBAction)addMarker {
		// Create a new region based on the center of the map view.
		CLLocationCoordinate2D coord = CLLocationCoordinate2DMake(_mapView.centerCoordinate.latitude, _mapView.centerCoordinate.longitude);
    
    CGPoint newCenter = [_mapView convertCoordinate:coord toPointToView:self.view];

    NSLog(@"CGPoint x: %f y: %f",newCenter.x,newCenter.y);
		
		// Create an annotation to show where the region is located on the map.
		Marker *myMarker = [[Marker alloc] initWithCoordinate:&coord];
		
		[_mapView addAnnotation:myMarker];
}

- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id <MKAnnotation>)annotation
{
    // this part is boilerplate code used to create or reuse a pin annotation
    static NSString *viewId = @"MKPinAnnotationView";
    MKPinAnnotationView *annotationView = (MKPinAnnotationView*)
    [self.mapView dequeueReusableAnnotationViewWithIdentifier:viewId];
    if (annotationView == nil) {
        annotationView = [[MKPinAnnotationView alloc]
                           initWithAnnotation:annotation reuseIdentifier:viewId];
    }
    // set your custom image
    annotationView.image = [UIImage imageNamed:@"caution.png"];
    
    // set position for annotation
    [annotationView setCenter:mapView.window.center];
    return annotationView;
}

#pragma mark MKMapViewDelegate overwrite methods

- (void) mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray *)views {
    
    //get whole screen
    CGRect visibleRect = [mapView annotationVisibleRect];
    
    //for any MKAnnotationView do...
    for (MKAnnotationView *view in views) {
        //end
        CGRect endFrame = view.frame;

        //start
        CGRect startFrame = endFrame;
        startFrame.origin.y = visibleRect.origin.y - startFrame.size.height;
        view.frame = startFrame;
        
        [UIView beginAnimations:@"drop" context:NULL];
        [UIView setAnimationDuration:0.5];
        
        view.frame = endFrame;
        
        [UIView commitAnimations];
    }
}

- (void)mapView:(MKMapView *)mapView regionWillChangeAnimated:(BOOL)animated{
    NSLog(@"regionWillChangeAnimated");
    
    [self pushMarker];

}

- (void)mapView:(MKMapView *)mapView regionDidChangeAnimated:(BOOL)animated{
    NSLog(@"regionDidChangeAnimated");
    
    [self dropMarker];
}

- (void)pushMarker{
    [UIView beginAnimations:@"drop" context:NULL];
    [UIView setAnimationDuration:0.2];
    
    defaultMarkerImageView.frame = upPosition;
    
    [UIView commitAnimations];
}

-(void)dropMarker{
    [UIView beginAnimations:@"drop" context:NULL];
    [UIView setAnimationDuration:0.5];
    
    defaultMarkerImageView.frame = downPosition;
    
    [UIView commitAnimations];
}

@end
